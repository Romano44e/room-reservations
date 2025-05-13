package com.room_reservations.facade;

import com.room_reservations.domain.*;
import com.room_reservations.service.ReservationService;
import com.room_reservations.service.RoomFilterStrategy.RoomByCapacityStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByDateTimeStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByLocationStrategy;
import com.room_reservations.service.RoomFilterStrategy.RoomByPriceStrategy;
import com.room_reservations.service.RoomService;
import com.room_reservations.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacadeTestSuite {

    @Mock
    private UserService userService;
    @Mock private RoomService roomService;
    @Mock private ReservationService reservationService;

    @Mock private RoomByLocationStrategy locationStrategy;
    @Mock private RoomByCapacityStrategy capacityStrategy;
    @Mock private RoomByPriceStrategy priceStrategy;
    @Mock private RoomByDateTimeStrategy dateTimeStrategy;

    @InjectMocks
    private RoomReservationFacade facade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = List.of(new User());
        when(userService.getAllUsers()).thenReturn(users);
        assertEquals(users, facade.getAllUsers());
    }

    @Test void shouldGetUsersByName() {
        List<User> users = List.of(new User());
        when(userService.getUserByName("John")).thenReturn(users);
        assertEquals(users, facade.getUsersByName("John"));
    }

    @Test void shouldGetUsersByEmail() {
        List<User> users = List.of(new User());
        when(userService.getUserByEmail("mail@example.com")).thenReturn(users);
        assertEquals(users, facade.getUsersByEmail("mail@example.com"));
    }

    @Test void shouldDeleteUserByPassword() {
        facade.deleteUserByPassword("secret");
        verify(userService).deleteUserByPassword("secret");
    }

    @Test void shouldSaveUser() {
        User user = new User();
        facade.saveUser(user);
        verify(userService).save(user);
    }

    @Test void shouldUpdateUser() {
        UserDto dto = new UserDto();
        User user = new User();
        when(userService.updateUserByPassword(dto)).thenReturn(user);
        assertEquals(user, facade.updateUser(dto));
    }

    @Test void shouldGetAllRooms() {
        List<Room> rooms = List.of(new Room());
        when(roomService.getAllRooms()).thenReturn(rooms);
        assertEquals(rooms, facade.getAllRooms());
    }

    @Test void shouldFilterRoomsByLocation() {
        List<Room> rooms = List.of(new Room());
        when(roomService.filterRooms(locationStrategy, "Warsaw")).thenReturn(rooms);
        assertEquals(rooms, facade.filterByLocation("Warsaw"));
    }

    @Test void shouldFilterRoomsByCapacity() {
        List<Room> rooms = List.of(new Room());
        when(roomService.filterRooms(capacityStrategy, 5)).thenReturn(rooms);
        assertEquals(rooms, facade.filterByCapacity(5));
    }

    @Test void shouldFilterRoomsByPrice() {
        List<Room> rooms = List.of(new Room());
        when(roomService.filterRooms(priceStrategy, new BigDecimal("100"))).thenReturn(rooms);
        assertEquals(rooms, facade.filterByPrice(new BigDecimal("100")));
    }

    @Test void shouldCheckRoomAvailability() {
        RoomByDateTimeInputDto input = new RoomByDateTimeInputDto();
        RoomByDateTimeOutputDto output = new RoomByDateTimeOutputDto("Available");
        when(roomService.filterRooms(dateTimeStrategy, input)).thenReturn(output);
        assertEquals(output, facade.checkAvailability(input));
    }

    @Test void shouldSaveRoom() {
        Room room = new Room();
        facade.saveRoom(room);
        verify(roomService).save(room);
    }

    @Test void shouldDeleteRoomByCipher() {
        facade.deleteRoomByCipher("abc123");
        verify(roomService).deleteRoomByCipher("abc123");
    }

    @Test void shouldUpdateRoom() {
        RoomDto dto = new RoomDto();
        Room room = new Room();
        when(roomService.updateByCipher(dto)).thenReturn(room);
        assertEquals(room, facade.updateRoom(dto));
    }

    @Test void shouldGetAllReservations() {
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getAllReservations()).thenReturn(reservations);
        assertEquals(reservations, facade.getAllReservations());
    }

    @Test void shouldGetReservationsByUserId() {
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationsByUserId(1L)).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByUserId(1L));
    }

    @Test void shouldGetReservationsByRoomId() {
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationsByRoomId(1L)).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByRoomId(1L));
    }

    @Test void shouldGetReservationsByDateTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationsByDateTime(now)).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByDateTime(now));
    }

    @Test void shouldGetReservationsByPaymentStatus() {
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationsByPaymentStatus("PAID")).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByPaymentStatus("PAID"));
    }

    @Test void shouldGetReservationsByReservationStatus() {
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationsByReservationStatus("CONFIRMED")).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByReservationStatus("CONFIRMED"));
    }

    @Test void shouldGetReservationsByAmount() {
        BigDecimal amount = new BigDecimal("100.00");
        List<Reservation> reservations = List.of(new Reservation());
        when(reservationService.getReservationByAmount(amount)).thenReturn(reservations);
        assertEquals(reservations, facade.getReservationsByAmount(amount));
    }

    @Test void shouldCreateReservation() {
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        Reservation reservation = new Reservation();
        when(reservationService.save(dto)).thenReturn(reservation);
        assertEquals(reservation, facade.createReservation(dto));
    }

    @Test void shouldDeleteReservationByCode() {
        facade.deleteReservationByCode("abc123");
        verify(reservationService).deleteReservationByCode("abc123");
    }

    @Test void shouldUpdateReservation() {
        ReservationDto dto = new ReservationDto();
        Reservation reservation = new Reservation();
        when(reservationService.updateReservationByCode(dto)).thenReturn(reservation);
        assertEquals(reservation, facade.updateReservation(dto));
    }

}
