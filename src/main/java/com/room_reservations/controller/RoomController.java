package com.room_reservations.controller;


import com.room_reservations.domain.RoomByDateTimeInputDto;
import com.room_reservations.domain.RoomByDateTimeOutputDto;
import com.room_reservations.domain.RoomDto;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.RoomMapper;
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

    private final RoomReservationFacade facade;
    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomMapper.mapToRoomDtoList(facade.getAllRooms()));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(roomMapper.mapToRoomDtoList(facade.filterByLocation(location)));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByCapacity(@PathVariable int capacity) {
        return ResponseEntity.ok(roomMapper.mapToRoomDtoList(facade.filterByCapacity(capacity)));
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<RoomDto>> getAllRoomsByPrice(@PathVariable BigDecimal price) {
        return ResponseEntity.ok(roomMapper.mapToRoomDtoList(facade.filterByPrice(price)));
    }

    @PostMapping(value = "/availability", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomByDateTimeOutputDto> checkRoomAvailability(@RequestBody RoomByDateTimeInputDto inputDto) {
        return ResponseEntity.ok(facade.checkAvailability(inputDto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRoom(@RequestBody RoomDto roomDto) {
        facade.saveRoom(roomMapper.mapToRoom(roomDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cipher/{cipher}")
    public ResponseEntity<Void> deleteRoomByCipher(@PathVariable String cipher) {
        facade.deleteRoomByCipher(cipher);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        facade.deleteRoomById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDto> updateRoom(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomMapper.mapToRoomDto(facade.updateRoom(roomDto)));
    }
}
