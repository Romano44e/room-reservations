package com.room_reservations.controller;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationDto;
import com.room_reservations.domain.ReservationPostInputDto;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.ReservationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationControllerTestSuite {

    @Mock
    private RoomReservationFacade facade;

    @Mock
    private ReservationMapper mapper;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReservations() {
        // Given
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getAllReservations()).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getAllReservations();

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByUserId() {
        // Given
        Long userId = 1L;
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByUserId(userId)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationByUserId(userId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByRoomId() {
        // Given
        Long roomId = 1L;
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByRoomId(roomId)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationByRoomId(roomId);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByDateTime() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByDateTime(now)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationByDateTime(now);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByPaymentStatus() {
        // Given
        String status = "PAID";
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByPaymentStatus(status)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationByPaymentStatus(status);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByReservationStatus() {
        // Given
        String status = "CONFIRMED";
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByReservationStatus(status)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationByReservationStatus(status);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldReturnReservationsByAmount() {
        // Given
        BigDecimal amount = new BigDecimal("150.00");
        List<Reservation> reservations = List.of(new Reservation());
        List<ReservationDto> dtos = List.of(new ReservationDto());
        when(facade.getReservationsByAmount(amount)).thenReturn(reservations);
        when(mapper.mapToReservationDtoList(reservations)).thenReturn(dtos);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationsGreaterOrEqualThanAmount(amount);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        Reservation reservation = new Reservation();
        when(facade.createReservation(dto)).thenReturn(reservation);

        // When
        ResponseEntity<Void> response = reservationController.createReservation(dto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldReturnBadRequestWhenCreateReservationFails() {
        // Given
        ReservationPostInputDto dto = new ReservationPostInputDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PLN", "test code");
        when(facade.createReservation(dto)).thenReturn(null);

        // When
        ResponseEntity<Void> response = reservationController.createReservation(dto);

        // Then
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void shouldDeleteReservationByCode() {
        // Given
        String code = "res123";

        // When
        ResponseEntity<Void> response = reservationController.deleteReservationByCode(code);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(facade).deleteReservationByCode(code);
    }

    @Test
    void shouldUpdateReservation() {
        // Given
        ReservationDto dto = new ReservationDto();
        Reservation reservation = new Reservation();
        ReservationDto updated = new ReservationDto();
        when(facade.updateReservation(dto)).thenReturn(reservation);
        when(mapper.mapToReservationDto(reservation)).thenReturn(updated);

        // When
        ResponseEntity<ReservationDto> response = reservationController.updateReservation(dto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

}
