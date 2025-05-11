package com.room_reservations.controller;

import com.room_reservations.domain.UserDto;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomreservations/users")
public class UserController {

    private final RoomReservationFacade facade;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.mapToUserDtoList(facade.getAllUsers()));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userMapper.mapToUserDtoList(facade.getUsersByName(name)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.mapToUserDtoList(facade.getUsersByEmail(email)));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.mapToUserDto(facade.getUserById(id)));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        facade.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/password/{password}")
    public ResponseEntity<Void> deleteUserByPassword(@PathVariable String password) {
        facade.deleteUserByPassword(password);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        facade.saveUser(userMapper.mapToUser(userDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.mapToUserDto(facade.updateUser(userDto)));
    }
}
