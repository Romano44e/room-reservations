package com.room_reservations.controller;

import com.room_reservations.domain.Room;
import com.room_reservations.domain.RoomByDateTimeInputDto;
import com.room_reservations.domain.RoomByDateTimeOutputDto;
import com.room_reservations.domain.RoomDto;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomControllerTestSuite {

    @Mock
    private RoomReservationFacade facade;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllRooms() {
        // Given
        List<Room> rooms = List.of(new Room());
        List<RoomDto> roomDtos = List.of(new RoomDto());
        when(facade.getAllRooms()).thenReturn(rooms);
        when(roomMapper.mapToRoomDtoList(rooms)).thenReturn(roomDtos);

        // When
        ResponseEntity<List<RoomDto>> response = roomController.getAllRooms();

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDtos, response.getBody());
    }

    @Test
    void shouldReturnRoomsByLocation() {
        // Given
        String location = "Warsaw";
        List<Room> rooms = List.of(new Room());
        List<RoomDto> roomDtos = List.of(new RoomDto());
        when(facade.filterByLocation(location)).thenReturn(rooms);
        when(roomMapper.mapToRoomDtoList(rooms)).thenReturn(roomDtos);

        // When
        ResponseEntity<List<RoomDto>> response = roomController.getAllRoomsByLocation(location);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDtos, response.getBody());
    }

    @Test
    void shouldReturnRoomsByCapacity() {
        // Given
        int capacity = 20;
        List<Room> rooms = List.of(new Room());
        List<RoomDto> roomDtos = List.of(new RoomDto());
        when(facade.filterByCapacity(capacity)).thenReturn(rooms);
        when(roomMapper.mapToRoomDtoList(rooms)).thenReturn(roomDtos);

        // When
        ResponseEntity<List<RoomDto>> response = roomController.getAllRoomsByCapacity(capacity);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDtos, response.getBody());
    }

    @Test
    void shouldReturnRoomsByPrice() {
        // Given
        BigDecimal price = new BigDecimal("100");
        List<Room> rooms = List.of(new Room());
        List<RoomDto> roomDtos = List.of(new RoomDto());
        when(facade.filterByPrice(price)).thenReturn(rooms);
        when(roomMapper.mapToRoomDtoList(rooms)).thenReturn(roomDtos);

        // When
        ResponseEntity<List<RoomDto>> response = roomController.getAllRoomsByPrice(price);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomDtos, response.getBody());
    }

    @Test
    void shouldCheckRoomAvailability() {
        // Given
        RoomByDateTimeInputDto inputDto = new RoomByDateTimeInputDto();
        RoomByDateTimeOutputDto outputDto = new RoomByDateTimeOutputDto("Available");
        when(facade.checkAvailability(inputDto)).thenReturn(outputDto);

        // When
        ResponseEntity<RoomByDateTimeOutputDto> response = roomController.checkRoomAvailability(inputDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(outputDto, response.getBody());
    }

    @Test
    void shouldCreateRoom() {
        // Given
        RoomDto roomDto = new RoomDto();
        Room room = new Room();
        when(roomMapper.mapToRoom(roomDto)).thenReturn(room);

        // When
        ResponseEntity<Void> response = roomController.createRoom(roomDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(facade).saveRoom(room);
    }

    @Test
    void shouldDeleteRoomByCipher() {
        // Given
        String cipher = "abc123";

        // When
        ResponseEntity<Void> response = roomController.deleteRoomByCipher(cipher);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(facade).deleteRoomByCipher(cipher);
    }

    @Test
    void shouldUpdateRoom() {
        // Given
        RoomDto roomDto = new RoomDto();
        Room room = new Room();
        RoomDto updatedDto = new RoomDto();
        when(facade.updateRoom(roomDto)).thenReturn(room);
        when(roomMapper.mapToRoomDto(room)).thenReturn(updatedDto);

        // When
        ResponseEntity<RoomDto> response = roomController.updateRoom(roomDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedDto, response.getBody());
    }

}
