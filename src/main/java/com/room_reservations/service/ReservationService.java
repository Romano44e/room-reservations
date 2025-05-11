package com.room_reservations.service;

import com.room_reservations.domain.*;
import com.room_reservations.repository.ReservationRepository;
import com.room_reservations.repository.RoomRepository;
import com.room_reservations.repository.UserRepository;
import com.room_reservations.service.exchangerateservice.NbpApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final RandomwordService randomwordService;
    private final NbpApiService nbpApiService;

    public Reservation save(ReservationPostInputDto reservationPostInputDto) {
        if (reservationPostInputDto.getStartDateTime().isAfter(reservationPostInputDto.getEndDateTime())) {
            return null;
        }

        Optional<Room> byId1 = roomRepository.findById(reservationPostInputDto.getRoomId());
        Room room = byId1.get();
        String name = room.getName();

        RoomByDateTimeOutputDto roomByDateTime = roomService.getRoomByDateTime(new RoomByDateTimeInputDto(name, reservationPostInputDto.getStartDateTime(), reservationPostInputDto.getEndDateTime()));
        String availibility = roomByDateTime.getAvailibility();
        if (availibility.equals("room is reserved. Choose a different time")) {
            return null;
        }

        Optional<User> byId = userRepository.findById(reservationPostInputDto.getUserId());
        User user = byId.get();
        int points = user.getPoints();
        if (points < 20) {
            return null;
        }

        int pointsAfterReservation = updateUsersPointsWhenReserving(user);
        user.setPoints(pointsAfterReservation);
        userRepository.save(user);

        Reservation reservation = new Reservation();
        reservation.setUserId(reservationPostInputDto.getUserId());
        reservation.setRoomId(reservationPostInputDto.getRoomId());
        reservation.setStartDateTime(reservationPostInputDto.getStartDateTime());
        reservation.setEndDateTime(reservationPostInputDto.getEndDateTime());
        reservation.setReservationStatus(String.valueOf(ReservationStatus.NEW));
        reservation.setPaymentStatus(String.valueOf(PaymentStatus.UNPAID));
        reservation.setCurrency(reservationPostInputDto.getCurrency());

        BigDecimal totalReservation = getTotalReservation(reservationPostInputDto);

        reservation.setAmount(totalReservation);

        String randomWord = randomwordService.generateRandomWord();
        if (randomWord.equals("Failed to download random word")) {
            String randomWord2 = randomwordService.generateRandomWord2();
            reservation.setCode(randomWord2);
            return reservationRepository.save(reservation);
        }

        reservation.setCode(randomWord);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByUserId = all.stream()
                .filter(reservation -> reservation.getUserId().equals(userId))
                .toList();
        return reservationListByUserId;
    }

    public List<Reservation> getReservationsByRoomId(Long roomId) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByRoomId = all.stream()
                .filter(reservation -> reservation.getRoomId().equals(roomId))
                .toList();
        return reservationListByRoomId;
    }


    public List<Reservation> getReservationsByDateTime(LocalDateTime dateTime) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByDateTime = all.stream()
                .filter(reservation -> (reservation.getStartDateTime().isBefore(dateTime) && reservation.getEndDateTime().isAfter(dateTime))
                || reservation.getStartDateTime().equals(dateTime) || reservation.getEndDateTime().equals(dateTime))
                .toList();
        return reservationListByDateTime;
    }


    public List<Reservation> getReservationsByPaymentStatus(String paymentStatus) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByPaymentStatus = all.stream()
                .filter(reservation -> reservation.getPaymentStatus().equals(paymentStatus))
                .toList();
        return reservationListByPaymentStatus;
    }


    public List<Reservation> getReservationsByReservationStatus(String reservationStatus) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByReservationStatus = all.stream()
                .filter(reservation -> reservation.getReservationStatus().equals(reservationStatus))
                .toList();
        return reservationListByReservationStatus;
    }


    public List<Reservation> getReservationByAmount(BigDecimal amount) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> reservationListByAmount = all.stream()
                .filter(reservation -> reservation.getAmount().toBigInteger().doubleValue() >= amount.toBigInteger().doubleValue())
                .toList();
        return reservationListByAmount;
    }

    public void deleteReservationByCode(String code) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> list = all.stream()
                .filter(reservation -> reservation.getCode().equals(code))
                .toList();
        Long id = list.get(0).getId();
        Reservation reservation = list.get(0);
        if (LocalDateTime.now().isBefore(reservation.getStartDateTime().minusDays(1))) {
            Long userId = reservation.getUserId();
            userService.updateUserPointsWhenCancelledById(userId);
        }

        reservationRepository.deleteById(id);
    }

    public void deleteReservationById(Long Id) {
        reservationRepository.deleteById(Id);
    }

    public Reservation updateReservationByCode(final ReservationDto reservationDto) {
        String code = reservationDto.getCode();
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> list = all.stream()
                .filter(reservation -> reservation.getCode().equals(code))
                .toList();

        Reservation reservation = list.getFirst();
        reservation.setRoomId(reservationDto.getRoomId());
        reservation.setStartDateTime(reservationDto.getStartDateTime());
        reservation.setEndDateTime(reservationDto.getEndDateTime());
        reservation.setPaymentStatus(reservationDto.getPaymentStatus());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        reservation.setAmount(reservationDto.getAmount());
        reservation.setCurrency(reservationDto.getCurrency());

        return reservationRepository.save(reservation);
    }

    public int updateUsersPointsWhenReserving(User user) {
        int pointsBeforeReservation = user.getPoints();
        int pointsAfterReservation = pointsBeforeReservation - 20;
        return pointsAfterReservation;
    }

    public BigDecimal getTotalReservation(ReservationPostInputDto reservationPostInputDto) {
        Long roomId = reservationPostInputDto.getRoomId();
        Optional<Room> byId = roomRepository.findById(roomId);
        Room room = byId.get();
        BigDecimal price = room.getPrice();
        LocalDateTime startDateTime = reservationPostInputDto.getStartDateTime();
        LocalDateTime endDateTime = reservationPostInputDto.getEndDateTime();
        int durationOfReservation = endDateTime.getHour() - startDateTime.getHour();

        if (reservationPostInputDto.getCurrency().equals(String.valueOf(ReservationCurrency.EUR))) {
            return price.multiply(BigDecimal.valueOf(durationOfReservation)).divide(BigDecimal.valueOf(nbpApiService.getEurExchangeRate()), 2, RoundingMode.HALF_UP);
        }
        return price.multiply(BigDecimal.valueOf(durationOfReservation));
    }

}
