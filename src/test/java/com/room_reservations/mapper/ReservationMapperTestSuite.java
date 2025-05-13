package com.room_reservations.mapper;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationMapperTestSuite {

    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        reservationMapper = new ReservationMapper();
    }

    @Test
    void shouldMapReservationDtoToReservation() {
        // Given
        ReservationDto dto = new ReservationDto(1L, 2L, 3L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "CONFIRMED", "PAID", "PLN", new BigDecimal("123.45"), "abc123");

        // When
        Reservation reservation = reservationMapper.mapToReservation(dto);

        // Then
        assertEquals(dto.getId(), reservation.getId());
        assertEquals(dto.getUserId(), reservation.getUserId());
        assertEquals(dto.getRoomId(), reservation.getRoomId());
        assertEquals(dto.getStartDateTime(), reservation.getStartDateTime());
        assertEquals(dto.getEndDateTime(), reservation.getEndDateTime());
        assertEquals(dto.getReservationStatus(), reservation.getReservationStatus());
        assertEquals(dto.getPaymentStatus(), reservation.getPaymentStatus());
        assertEquals(dto.getCurrency(), reservation.getCurrency());
        assertEquals(dto.getAmount(), reservation.getAmount());
        assertEquals(dto.getCode(), reservation.getCode());
    }

    @Test
    void shouldMapReservationToReservationDto() {
        // Given
        Reservation reservation = new Reservation(4L, 5L, 6L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "NEW", "UNPAID", "EUR", new BigDecimal("456.78"), "xyz789");

        // When
        ReservationDto dto = reservationMapper.mapToReservationDto(reservation);

        // Then
        assertEquals(reservation.getId(), dto.getId());
        assertEquals(reservation.getUserId(), dto.getUserId());
        assertEquals(reservation.getRoomId(), dto.getRoomId());
        assertEquals(reservation.getStartDateTime(), dto.getStartDateTime());
        assertEquals(reservation.getEndDateTime(), dto.getEndDateTime());
        assertEquals(reservation.getReservationStatus(), dto.getReservationStatus());
        assertEquals(reservation.getPaymentStatus(), dto.getPaymentStatus());
        assertEquals(reservation.getCurrency(), dto.getCurrency());
        assertEquals(reservation.getAmount(), dto.getAmount());
        assertEquals(reservation.getCode(), dto.getCode());
    }

    @Test
    void shouldMapReservationListToReservationDtoList() {
        // Given
        List<Reservation> reservations = List.of(
                new Reservation(1L, 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "NEW", "UNPAID", "PLN", new BigDecimal("100"), "c1"),
                new Reservation(2L, 2L, 2L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "CONFIRMED", "PAID", "EUR", new BigDecimal("200"), "c2")
        );

        // When
        List<ReservationDto> dtos = reservationMapper.mapToReservationDtoList(reservations);

        // Then
        assertEquals(2, dtos.size());
        assertEquals("NEW", dtos.get(0).getReservationStatus());
        assertEquals("CONFIRMED", dtos.get(1).getReservationStatus());
    }

}
