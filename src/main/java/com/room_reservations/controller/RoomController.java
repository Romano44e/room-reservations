package com.room_reservations.controller;


import com.room_reservations.domain.Room;
import com.room_reservations.domain.RoomByDateTimeInputDto;
import com.room_reservations.domain.RoomByDateTimeOutputDto;
import com.room_reservations.domain.RoomDto;
import com.room_reservations.mapper.RoomMapper;
import com.room_reservations.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomreservations/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<Room> allRooms = roomService.getAllRooms();
        List<RoomDto> roomDtoList = roomMapper.mapToRoomDtoList(allRooms);
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping(value = "/location/{location}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByLocation(@PathVariable String location) {
        List<Room> roomsByLocation = roomService.getRoomsByLocation(location);
        List<RoomDto> roomDtoList = roomMapper.mapToRoomDtoList(roomsByLocation);
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping(value = "/capacity/{capacity}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByCapacity(@PathVariable int capacity) {
        List<Room> roomsByCapacity = roomService.getRoomsByCapacity(capacity);
        List<RoomDto> roomDtoList = roomMapper.mapToRoomDtoList(roomsByCapacity);
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping(value = "/price/{price}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByPrice(@PathVariable BigDecimal price) {
        List<Room> roomsByPrice = roomService.getRoomsByPrice(price);
        List<RoomDto> roomDtoList = roomMapper.mapToRoomDtoList(roomsByPrice);
        return ResponseEntity.ok(roomDtoList);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomByDateTimeOutputDto> checkRoomAvailibilityByDateTime(@RequestBody RoomByDateTimeInputDto roomByDateTimeInputDto) {
        RoomByDateTimeOutputDto roomByDateTimeOutputDto = roomService.getRoomByDateTime(roomByDateTimeInputDto);
        return ResponseEntity.ok(roomByDateTimeOutputDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRoom(@RequestBody RoomDto roomDto) {
        Room room = roomMapper.mapToRoom(roomDto);
        roomService.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/cipher/{cipher}")
    public ResponseEntity<Void> deleteRoomByCipher(@PathVariable String cipher) {
        roomService.deleteRoomByCipher(cipher);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDto> updateRoom(@RequestBody RoomDto roomDto) {
        Room room = roomService.updateByCipher(roomDto);
        RoomDto roomDto1 = roomMapper.mapToRoomDto(room);
        return ResponseEntity.ok(roomDto1);
    }

}
