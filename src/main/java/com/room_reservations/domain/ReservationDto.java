package com.room_reservations.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationDto {

    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reservationStatus;
    private String paymentStatus;
    private String currency;
    private BigDecimal amount;
    private String code;

    public ReservationDto(long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String paymentStatus, String reservationStatus, BigDecimal amount, String currency, String code) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
        this.amount = amount;
        this.currency = currency;
        this.code = code;
    }
}
