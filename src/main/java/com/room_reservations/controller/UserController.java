package com.room_reservations.controller;

import com.room_reservations.domain.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/roomreservations/users")
public class UserController {

    @GetMapping
    public List<UserDto> getUsers() {
        return new ArrayList<>();
    }

    

}
