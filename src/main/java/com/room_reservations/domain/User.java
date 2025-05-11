package com.room_reservations.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "USERS")
public class User {

    public User(String name, String email, int points) {
        this.name = name;
        this.email = email;
        this.points = points;
    }
    public User(String name, String email, int points, String password) {
        this.name = name;
        this.email = email;
        this.points = points;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "POINTS")
    private int points;

    @Column(name = "PASSWORD", unique = true, nullable = false)
    private String password;

}
