package com.room_reservations.mapper;

import com.room_reservations.domain.Reservation;
import com.room_reservations.domain.ReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationMapper {

    public Reservation mapToReservation(ReservationDto reservationDto) {
        return new Reservation(reservationDto.getId(), reservationDto.getUserId(), reservationDto.getRoomId(), reservationDto.getStartDateTime(), reservationDto.getEndDateTime(), reservationDto.getReservationStatus(), reservationDto.getPaymentStatus(), reservationDto.getCurrency(), reservationDto.getAmount(), reservationDto.getCode());
    }

    public ReservationDto mapToReservationDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(), reservation.getUserId(), reservation.getRoomId(), reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getReservationStatus(), reservation.getPaymentStatus(), reservation.getCurrency(), reservation.getAmount(), reservation.getCode());
    }

    public List<ReservationDto> mapToReservationDtoList(List<Reservation> reservations) {
        return reservations.stream().map(this::mapToReservationDto).collect(Collectors.toList());
    }

}
