package com.room_reservations.service;

import com.room_reservations.domain.*;
import com.room_reservations.repository.ReservationRepository;
import com.room_reservations.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public RoomByDateTimeOutputDto getRoomByDateTime(RoomByDateTimeInputDto roomByDateTimeInputDto) {
        List<Room> all = roomRepository.findAll();
        Room room = all.stream()
                .filter(r -> r.getName().equals(roomByDateTimeInputDto.getName()))
                .findFirst()
                .get();

        Long id = room.getId();
        List<Reservation> allReservations = reservationRepository.findAll();
        List<Reservation> reservationListByRoom = allReservations.stream()
                .filter(reservation -> reservation.getRoomId().equals(id))
                .toList();

        List<Reservation> reservationListByDateTime = reservationListByRoom.stream()
                .filter(reservation -> reservation.getStartDateTime().isBefore(roomByDateTimeInputDto.getStartDateTime())
                        && reservation.getEndDateTime().isAfter(roomByDateTimeInputDto.getStartDateTime())
                || reservation.getEndDateTime().isAfter(roomByDateTimeInputDto.getEndDateTime())
                        && reservation.getStartDateTime().isBefore(roomByDateTimeInputDto.getEndDateTime())
                || reservation.getStartDateTime().isAfter(roomByDateTimeInputDto.getStartDateTime())
                && reservation.getStartDateTime().isBefore(roomByDateTimeInputDto.getEndDateTime()))
                .toList();

        if (reservationListByDateTime.isEmpty()) {
            return new RoomByDateTimeOutputDto("room is available to reservation");
        }
        return new RoomByDateTimeOutputDto("room is reserved. Choose a different time");
    }

    public List<Room> getRoomsByCapacity (int capacity) {
        List<Room> all = roomRepository.findAll();
        List<Room> roomListByCapacity = all.stream()
                .filter(r -> r.getCapacity() >= capacity)
                .toList();
        return roomListByCapacity;
    }

    public List<Room> getRoomsByLocation (String location) {
        List<Room> all = roomRepository.findAll();
        List<Room> roomListByLocation = all.stream()
                .filter(r -> r.getLocation().equals(location))
                .toList();
        return roomListByLocation;
    }

    public List<Room> getRoomsByPrice (BigDecimal price) {
        List<Room> all = roomRepository.findAll();
        List<Room> roomListByPrice = all.stream()
                .filter(r -> r.getPrice().equals(price))
                .toList();
        return roomListByPrice;
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    public void deleteRoomByCipher(final String cipher) {
        List<Room> all = roomRepository.findAll();
        List<Room> roomList = all.stream().filter(room -> room.getCipher().equals(cipher))
                .toList();
        Long id = roomList.get(0).getId();
        roomRepository.deleteById(id);
    }

    public Room updateByCipher(final RoomDto roomDto) {
        String key = roomDto.getCipher();
        List<Room> all = roomRepository.findAll();
        List<Room> roomListByKey = all.stream()
                .filter(r -> r.getCipher().equals(key))
                .toList();

        Room roomyByKey = roomListByKey.getFirst();
        roomyByKey.setName(roomDto.getName());
        roomyByKey.setCapacity(roomDto.getCapacity());
        roomyByKey.setLocation(roomDto.getLocation());
        roomyByKey.setPrice(roomDto.getPrice());

        return roomRepository.save(roomyByKey);
    }

}
