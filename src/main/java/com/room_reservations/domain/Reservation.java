package com.room_reservations.domain;


import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "RESERVATIONS")
public class Reservation {

    public Reservation(Long userId, Long roomId, LocalDateTime startDateTime, LocalDateTime endDateTime, String reservationStatus, String paymentStatus, String currency, BigDecimal amount, String code) {
        this.userId = userId;
        this.roomId = roomId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reservationStatus = reservationStatus;
        this.paymentStatus = paymentStatus;
        this.currency = currency;
        this.amount = amount;
        this.code = code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "START_TIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_TIME")
    private LocalDateTime endDateTime;

    @Column(name = "RESERVATION_STATUS")
    private String reservationStatus;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CODE")
    private String code;

}
