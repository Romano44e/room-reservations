package com.room_reservations.service.RoomFilterStrategy;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.Room;
import com.room_reservations.domain.RoomByDateTimeInputDto;
import com.room_reservations.domain.RoomByDateTimeOutputDto;
import com.room_reservations.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomByDateTimeStrategy implements RoomFilterStrategy<RoomByDateTimeInputDto, RoomByDateTimeOutputDto> {

    private final ReservationRepository reservationRepository;

    public RoomByDateTimeStrategy(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public RoomByDateTimeOutputDto filter(List<Room> rooms, RoomByDateTimeInputDto input) {
        Room room = rooms.stream()
                .filter(r -> r.getName().equals(input.getName()))
                .findFirst()
                .orElseThrow();

        Long id = room.getId();
        List<Reservation> allReservations = reservationRepository.findAll();
        List<Reservation> reservationListByRoom = allReservations.stream()
                .filter(reservation -> reservation.getRoomId().equals(id))
                .toList();

        List<Reservation> reservationListByDateTime = reservationListByRoom.stream()
                .filter(reservation -> reservation.getStartDateTime().isBefore(input.getStartDateTime())
                        && reservation.getEndDateTime().isAfter(input.getStartDateTime())
                        || reservation.getEndDateTime().isAfter(input.getEndDateTime())
                        && reservation.getStartDateTime().isBefore(input.getEndDateTime())
                        || reservation.getStartDateTime().isAfter(input.getStartDateTime())
                        && reservation.getStartDateTime().isBefore(input.getEndDateTime()))
                .toList();

        if (reservationListByDateTime.isEmpty()) {
            return new RoomByDateTimeOutputDto("room is available to reservation");
        }
        return new RoomByDateTimeOutputDto("room is reserved. Choose a different time");
    }
}
