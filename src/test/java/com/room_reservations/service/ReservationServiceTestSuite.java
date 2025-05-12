package com.room_reservations.service;

import com.room_reservations.domain.*;
import com.room_reservations.repository.ReservationRepository;
import com.room_reservations.repository.RoomRepository;
import com.room_reservations.repository.UserRepository;
import com.room_reservations.service.exchangerateservice.NbpApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTestSuite {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private RandomwordService randomwordService;

    @Mock
    private NbpApiService nbpApiService;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnNullWhenStartDateAfterEndDate() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now(), "PLN", "test code");

        // When
        Reservation result = reservationService.save(dto);

        // Then
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenRoomUnavailable() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        Room room = new Room(1L, "RoomX", 10, "Loc", BigDecimal.TEN, "cipher");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomService.getRoomByDateTime(any())).thenReturn(new RoomByDateTimeOutputDto("room is reserved. Choose a different time"));

        // When
        Reservation result = reservationService.save(dto);

        // Then
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenUserPointsTooLow() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        Room room = new Room(1L, "RoomX", 10, "Loc", BigDecimal.TEN, "cipher");
        User user = new User(1L, "User", "mail", 10, "pass");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomService.getRoomByDateTime(any())).thenReturn(new RoomByDateTimeOutputDto("room is available to reservation"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        Reservation result = reservationService.save(dto);

        // Then
        assertNull(result);
    }

    @Test
    void shouldSaveReservationWithValidInput() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        Room room = new Room(1L, "RoomX", 10, "Loc", new BigDecimal("100"), "cipher");
        User user = new User(1L, "User", "mail", 100, "pass");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomService.getRoomByDateTime(any())).thenReturn(new RoomByDateTimeOutputDto("room is available to reservation"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(randomwordService.generateRandomWord()).thenReturn("code123");
        when(reservationRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        Reservation reservation = reservationService.save(dto);

        // Then
        assertNotNull(reservation);
        assertEquals("code123", reservation.getCode());
        assertEquals("NEW", reservation.getReservationStatus());
        assertEquals("UNPAID", reservation.getPaymentStatus());
        verify(userRepository).save(user);
    }

    @Test
    void shouldReturnReservationsByUserId() {
        // Given
        Reservation r1 = new Reservation();
        r1.setUserId(1L);
        Reservation r2 = new Reservation();
        r2.setUserId(2L);

        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reservation> result = reservationService.getReservationsByUserId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void shouldReturnReservationsByRoomId() {
        // Given
        Reservation r1 = new Reservation();
        r1.setRoomId(1L);
        Reservation r2 = new Reservation();
        r2.setRoomId(2L);
        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reservation> result = reservationService.getReservationsByRoomId(2L);

        // Then
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getRoomId());
    }

    @Test
    void shouldReturnReservationsByPaymentStatus() {
        // Given
        Reservation r1 = new Reservation();
        r1.setPaymentStatus("PAID");
        Reservation r2 = new Reservation();
        r2.setPaymentStatus("UNPAID");
        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reservation> result = reservationService.getReservationsByPaymentStatus("PAID");

        // Then
        assertEquals(1, result.size());
        assertEquals("PAID", result.get(0).getPaymentStatus());
    }

    @Test
    void shouldReturnReservationsByAmount() {
        // Given
        Reservation r1 = new Reservation();
        r1.setAmount(new BigDecimal("500"));
        Reservation r2 = new Reservation();
        r2.setAmount(new BigDecimal("100"));
        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Reservation> result = reservationService.getReservationByAmount(new BigDecimal("200"));

        // Then
        assertEquals(1, result.size());
        assertTrue(result.get(0).getAmount().compareTo(new BigDecimal("200")) >= 0);
    }

    @Test
    void shouldDeleteReservationByCodeAndUpdatePoints() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCode("abc123");
        reservation.setUserId(2L);
        reservation.setStartDateTime(LocalDateTime.now().plusDays(2));

        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        // When
        reservationService.deleteReservationByCode("abc123");

        // Then
        verify(userService).updateUserPointsWhenCancelledById(2L);
        verify(reservationRepository).deleteById(1L);
    }

    @Test
    void shouldUpdateReservationByCode() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setCode("xyz123");
        ReservationDto dto = new ReservationDto(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PAID", "CONFIRMED", new BigDecimal("150"), "PLN", "xyz123");

        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(reservationRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // When
        Reservation updated = reservationService.updateReservationByCode(dto);

        // Then
        assertEquals("PAID", updated.getPaymentStatus());
        assertEquals("CONFIRMED", updated.getReservationStatus());
        assertEquals(new BigDecimal("150"), updated.getAmount());
    }

    @Test
    void shouldCalculateTotalReservationInPLN() {
        // Given
        Room room = new Room(1L, "R1", 10, "Loc", new BigDecimal("100"), "c1");
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.of(2024,1,1,10,0), LocalDateTime.of(2024,1,1,12,0), "PLN", "test code");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // When
        BigDecimal result = reservationService.getTotalReservation(dto);

        // Then
        assertEquals(new BigDecimal("200"), result);
    }

    @Test
    void shouldCalculateTotalReservationInEUR() {
        // Given
        Room room = new Room(1L, "R1", 10, "Loc", new BigDecimal("100"), "c1");
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.of(2024,1,1,10,0), LocalDateTime.of(2024,1,1,12,0), "EUR", "test code");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(nbpApiService.getEurExchangeRate()).thenReturn(4.00);

        // When
        BigDecimal result = reservationService.getTotalReservation(dto);

        // Then
        assertEquals(new BigDecimal("50.00"), result);
    }

}
