package com.room_reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationPostInputDto {

    private Long userId;
    private Long roomId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String currency;
    private String code;

}
