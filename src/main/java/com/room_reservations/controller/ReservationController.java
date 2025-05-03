package com.room_reservations.controller;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationDto;
import com.room_reservations.domain.ReservationPostInputDto;
import com.room_reservations.mapper.ReservationMapper;
import com.room_reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomreservations/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationPostInputDto reservationPostInputDto) {
        Reservation reservation = reservationService.save(reservationPostInputDto);
        if(reservation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}

