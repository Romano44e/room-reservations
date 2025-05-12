package com.room_reservations.service;

import com.room_reservations.domain.*;
import com.room_reservations.repository.ReservationRepository;
import com.room_reservations.repository.RoomRepository;
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

public class RoomServiceTestSuite {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RandomwordService randomwordService;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveRoomWithGeneratedCipher() {
        // Given
        Room room = new Room();
        when(randomwordService.generateRandomWord()).thenReturn("cipher123");
        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Room savedRoom = roomService.save(room);

        // Then
        assertEquals("cipher123", savedRoom.getCipher());
        verify(roomRepository).save(room);
    }


    @Test
    void shouldReturnRoomAvailableMessageWhenNoReservationConflict() {
        // Given
        Room room = new Room(1L, "RoomA", 10, "Loc", new BigDecimal("100.00"), "cipher");
        RoomByDateTimeInputDto input = new RoomByDateTimeInputDto("RoomA", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(reservationRepository.findAll()).thenReturn(List.of());

        // When
        RoomByDateTimeOutputDto result = roomService.getRoomByDateTime(input);

        // Then
        assertEquals("room is available to reservation", result.getAvailibility());
    }

    @Test
    void shouldReturnRoomReservedMessageWhenReservationConflict() {
        // Given
        Room room = new Room(1L, "RoomA", 10, "Loc", new BigDecimal("100.00"), "cipher");
        LocalDateTime now = LocalDateTime.now();
        RoomByDateTimeInputDto input = new RoomByDateTimeInputDto("RoomA", now.plusHours(1), now.plusHours(2));

        Reservation conflictingReservation = new Reservation();
        conflictingReservation.setRoomId(1L);
        conflictingReservation.setStartDateTime(now.plusMinutes(90));
        conflictingReservation.setEndDateTime(now.plusHours(3));

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(reservationRepository.findAll()).thenReturn(List.of(conflictingReservation));

        // When
        RoomByDateTimeOutputDto result = roomService.getRoomByDateTime(input);

        // Then
        assertEquals("room is reserved. Choose a different time", result.getAvailibility());
    }

    @Test
    void shouldFilterRoomsByCapacity() {
        // Given
        Room r1 = new Room(1L, "R1", 10, "L1", new BigDecimal("100"), "c1");
        Room r2 = new Room(2L, "R2", 5, "L2", new BigDecimal("200"), "c2");
        when(roomRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Room> result = roomService.getRoomsByCapacity(6);

        // Then
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getName());
    }

    @Test
    void shouldFilterRoomsByLocation() {
        // Given
        Room r1 = new Room(1L, "R1", 10, "Warsaw", new BigDecimal("100"), "c1");
        Room r2 = new Room(2L, "R2", 5, "Krakow", new BigDecimal("200"), "c2");
        when(roomRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Room> result = roomService.getRoomsByLocation("Warsaw");

        // Then
        assertEquals(1, result.size());
        assertEquals("Warsaw", result.get(0).getLocation());
    }

    @Test
    void shouldFilterRoomsByPrice() {
        // Given
        Room r1 = new Room(1L, "R1", 10, "Warsaw", new BigDecimal("100"), "c1");
        Room r2 = new Room(2L, "R2", 5, "Krakow", new BigDecimal("200"), "c2");
        when(roomRepository.findAll()).thenReturn(List.of(r1, r2));

        // When
        List<Room> result = roomService.getRoomsByPrice(new BigDecimal("100"));

        // Then
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getName());
    }


    @Test
    void shouldDeleteRoomByCipher() {
        // Given
        Room room = new Room(1L, "R1", 10, "Warsaw", new BigDecimal("100"), "abc123");
        when(roomRepository.findAll()).thenReturn(List.of(room));

        // When
        roomService.deleteRoomByCipher("abc123");

        // Then
        verify(roomRepository).deleteById(1L);
    }

    @Test
    void shouldUpdateRoomByCipher() {
        // Given
        Room room = new Room(1L, "Old", 5, "OldLoc", new BigDecimal("50.00"), "key123");
        RoomDto dto = new RoomDto(1L,"New", 10, "NewLoc", new BigDecimal("99.99"), "key123");
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Room updated = roomService.updateByCipher(dto);

        // Then
        assertEquals("New", updated.getName());
        assertEquals(10, updated.getCapacity());
        assertEquals("NewLoc", updated.getLocation());
        assertEquals(new BigDecimal("99.99"), updated.getPrice());
        verify(roomRepository).save(room);
    }

}
