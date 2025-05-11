package com.room_reservations.controller;

import com.room_reservations.domain.*;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomreservations/reservations")
public class ReservationController {

    private final RoomReservationFacade facade;
    private final ReservationMapper mapper;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getAllReservations()));
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByUserId(userId)));
    }

    @GetMapping("/roomId/{roomId}")
    public ResponseEntity<List<ReservationDto>> getReservationByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByRoomId(roomId)));
    }

    @GetMapping("/dateTime/{dateTime}")
    public ResponseEntity<List<ReservationDto>> getReservationByDateTime(@PathVariable LocalDateTime dateTime) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByDateTime(dateTime)));
    }

    @GetMapping("/paymentStatus/{paymentStatus}")
    public ResponseEntity<List<ReservationDto>> getReservationByPaymentStatus(@PathVariable String paymentStatus) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByPaymentStatus(paymentStatus)));
    }

    @GetMapping("/reservationStatus/{reservationStatus}")
    public ResponseEntity<List<ReservationDto>> getReservationByReservationStatus(@PathVariable String reservationStatus) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByReservationStatus(reservationStatus)));
    }

    @GetMapping("/amount/{amount}")
    public ResponseEntity<List<ReservationDto>> getReservationsGreaterOrEqualThanAmount(@PathVariable BigDecimal amount) {
        return ResponseEntity.ok(mapper.mapToReservationDtoList(facade.getReservationsByAmount(amount)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationPostInputDto dto) {
        Reservation reservation = facade.createReservation(dto);
        return reservation != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/code/{code}")
    public ResponseEntity<Void> deleteReservationByCode(@PathVariable String code) {
        facade.deleteReservationByCode(code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        facade.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto dto) {
        return ResponseEntity.ok(mapper.mapToReservationDto(facade.updateReservation(dto)));
    }
}

