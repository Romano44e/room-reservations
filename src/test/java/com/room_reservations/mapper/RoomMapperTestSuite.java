package com.room_reservations.mapper;

import com.room_reservations.domain.Room;
import com.room_reservations.domain.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomMapperTestSuite {

    private RoomMapper roomMapper;

    @BeforeEach
    void setUp() {
        roomMapper = new RoomMapper();
    }

    @Test
    void shouldMapRoomDtoToRoom() {
        // Given
        RoomDto dto = new RoomDto(1L, "Conference Room", 50, "Floor 1", new BigDecimal("199.99"), "cipher123");

        // When
        Room room = roomMapper.mapToRoom(dto);

        // Then
        assertEquals(dto.getId(), room.getId());
        assertEquals(dto.getName(), room.getName());
        assertEquals(dto.getCapacity(), room.getCapacity());
        assertEquals(dto.getLocation(), room.getLocation());
        assertEquals(dto.getPrice(), room.getPrice());
        assertEquals(dto.getCipher(), room.getCipher());
    }

    @Test
    void shouldMapRoomToRoomDto() {
        // Given
        Room room = new Room(2L, "Meeting Room", 20, "Floor 2", new BigDecimal("99.99"), "code321");

        // When
        RoomDto dto = roomMapper.mapToRoomDto(room);

        // Then
        assertEquals(room.getId(), dto.getId());
        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getCapacity(), dto.getCapacity());
        assertEquals(room.getLocation(), dto.getLocation());
        assertEquals(room.getPrice(), dto.getPrice());
        assertEquals(room.getCipher(), dto.getCipher());
    }

    @Test
    void shouldMapRoomListToRoomDtoList() {
        // Given
        List<Room> rooms = List.of(
                new Room(1L, "Room A", 10, "Floor A", new BigDecimal("50.00"), "abc"),
                new Room(2L, "Room B", 20, "Floor B", new BigDecimal("75.00"), "def")
        );

        // When
        List<RoomDto> dtos = roomMapper.mapToRoomDtoList(rooms);

        // Then
        assertEquals(2, dtos.size());
        assertEquals("Room A", dtos.get(0).getName());
        assertEquals("Room B", dtos.get(1).getName());
    }

}
