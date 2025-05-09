package com.room_reservations.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "ROOMS")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CAPACITY")
    private int capacity;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CIPHER")
    private String cipher;

}
