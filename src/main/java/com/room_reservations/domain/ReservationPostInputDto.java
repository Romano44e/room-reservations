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

//    public ReservationPostInputDto() {
//
//    }
//
//    public ReservationPostInputDto(LocalDateTime startDateTime, LocalDateTime endDateTime) {
//        this.startDateTime = startDateTime;
//        this.endDateTime = endDateTime;
//    }

//    public ReservationPostInputDto(long userId, long roomId, LocalDateTime startDateTime, LocalDateTime endDateTime, String currency, String code) {
//        this.userId = userId;
//        this.roomId = roomId;
//        this.startDateTime = startDateTime;
//        this.endDateTime = endDateTime;
//        this.currency = currency;
//        this.code = code;
//    }

    public void setStartDateTime(LocalDateTime localDateTime) {
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
