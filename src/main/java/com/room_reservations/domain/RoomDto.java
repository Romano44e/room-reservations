package com.room_reservations.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RoomDto {

    private Long id;
    private String name;
    private int capacity;
    private String location;
    private BigDecimal price;
    private String cipher;

}
