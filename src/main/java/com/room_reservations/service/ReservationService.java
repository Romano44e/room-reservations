package com.room_reservations.service;

import com.room_reservations.domain.*;
import com.room_reservations.repository.ReservationRepository;
import com.room_reservations.repository.RoomRepository;
import com.room_reservations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

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
        reservation.setCode(reservationPostInputDto.getCode());


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
            return price.multiply(BigDecimal.valueOf(durationOfReservation)).divide(BigDecimal.valueOf(4));
        }
        return price.multiply(BigDecimal.valueOf(durationOfReservation));
    }

}
