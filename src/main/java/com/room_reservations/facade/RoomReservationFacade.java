package com.room_reservations.facade;

import com.room_reservations.domain.*;
import com.room_reservations.service.ReservationService;
import com.room_reservations.service.RoomFilterStrategy.RoomByCapacityStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByDateTimeStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByLocationStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByPriceStrategy;
import com.room_reservations.service.RoomService;
import com.room_reservations.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomReservationFacade {

    private final UserService userService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    private final RoomByLocationStrategy locationStrategy;
    private final RoomByCapacityStrategy capacityStrategy;
    private final RoomByPriceStrategy priceStrategy;
    private final RoomByDateTimeStrategy dateTimeStrategy;

    // USER
    public List<User> getAllUsers() { return userService.getAllUsers(); }
    public List<User> getUsersByName(String name) { return userService.getUserByName(name); }
    public List<User> getUsersByEmail(String email) { return userService.getUserByEmail(email); }
    public User getUserById(Long id) { return userService.getUserById(id); }
    public void deleteUserById(Long id) { userService.deleteUser(id); }
    public void deleteUserByPassword(String password) { userService.deleteUserByPassword(password); }
    public void saveUser(User user) { userService.save(user); }
    public User updateUser(UserDto userDto) { return userService.updateUserByPassword(userDto); }

    // ROOM
    public List<Room> getAllRooms() { return roomService.getAllRooms(); }
    public List<Room> filterByLocation(String location) { return roomService.filterRooms(locationStrategy, location); }
    public List<Room> filterByCapacity(int capacity) { return roomService.filterRooms(capacityStrategy, capacity); }
    public List<Room> filterByPrice(BigDecimal price) { return roomService.filterRooms(priceStrategy, price); }
    public RoomByDateTimeOutputDto checkAvailability(RoomByDateTimeInputDto dto) {
        return roomService.filterRooms(dateTimeStrategy, dto);
    }
    public void saveRoom(Room room) { roomService.save(room); }
    public void deleteRoomByCipher(String cipher) { roomService.deleteRoomByCipher(cipher); }
    public void deleteRoomById(Long id) { roomService.deleteRoomById(id); }
    public Room updateRoom(RoomDto roomDto) { return roomService.updateByCipher(roomDto); }

    // RESERVATION
    public List<Reservation> getAllReservations() { return reservationService.getAllReservations(); }
    public List<Reservation> getReservationsByUserId(Long userId) { return reservationService.getReservationsByUserId(userId); }
    public List<Reservation> getReservationsByRoomId(Long roomId) { return reservationService.getReservationsByRoomId(roomId); }
    public List<Reservation> getReservationsByDateTime(LocalDateTime dateTime) { return reservationService.getReservationsByDateTime(dateTime); }
    public List<Reservation> getReservationsByPaymentStatus(String paymentStatus) { return reservationService.getReservationsByPaymentStatus(paymentStatus); }
    public List<Reservation> getReservationsByReservationStatus(String reservationStatus) { return reservationService.getReservationsByReservationStatus(reservationStatus); }
    public List<Reservation> getReservationsByAmount(BigDecimal amount) { return reservationService.getReservationByAmount(amount); }
    public Reservation createReservation(ReservationPostInputDto dto) { return reservationService.save(dto); }
    public void deleteReservationByCode(String code) { reservationService.deleteReservationByCode(code); }
    public void deleteReservationById(Long id) { reservationService.deleteReservationById(id); }
    public Reservation updateReservation(ReservationDto dto) { return reservationService.updateReservationByCode(dto); }
}
