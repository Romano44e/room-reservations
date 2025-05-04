package com.room_reservations.controller;

import com.room_reservations.domain.*;
import com.room_reservations.mapper.ReservationMapper;
import com.room_reservations.service.ReservationService;
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

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/userId/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/roomId/{roomId}")
    public ResponseEntity<List<ReservationDto>> getReservationByRoomId(@PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.getReservationsByRoomId(roomId);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/dateTime/{dateTime}")
    public ResponseEntity<List<ReservationDto>> getReservationByDateTime(@PathVariable LocalDateTime dateTime) {
        List<Reservation> reservations = reservationService.getReservationsByDateTime(dateTime);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/paymentStatus/{paymentStatus}")
    public ResponseEntity<List<ReservationDto>> getReservationByPaymentStatus(@PathVariable String paymentStatus) {
        List<Reservation> reservations = reservationService.getReservationsByPaymentStatus(paymentStatus);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/reservationStatus/{reservationStatus}")
    public ResponseEntity<List<ReservationDto>> getReservationByReservationStatus(@PathVariable String reservationStatus) {
        List<Reservation> reservations = reservationService.getReservationsByReservationStatus(reservationStatus);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @GetMapping(value = "/amount/{amount}")
    public ResponseEntity<List<ReservationDto>> getReservationsGreaterOrEqualThanAmount(@PathVariable BigDecimal amount) {
        List<Reservation> reservations = reservationService.getReservationByAmount(amount);
        List<ReservationDto> reservationDtoList = reservationMapper.mapToReservationDtoList(reservations);
        return ResponseEntity.ok(reservationDtoList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationPostInputDto reservationPostInputDto) {
        Reservation reservation = reservationService.save(reservationPostInputDto);
        if(reservation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/code/{code}")
    public ResponseEntity<Void> deleteReservationByCode(@PathVariable String code) {
        reservationService.deleteReservationByCode(code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationService.updateReservationByCode(reservationDto);
        ReservationDto reservationDto1 = reservationMapper.mapToReservationDto(reservation);
        return ResponseEntity.ok(reservationDto1);
    }

}

