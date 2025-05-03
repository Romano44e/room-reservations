package com.room_reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RoomByDateTimeInputDto {

    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
