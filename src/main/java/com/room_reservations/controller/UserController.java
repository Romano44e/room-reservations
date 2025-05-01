package com.room_reservations.controller;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import com.room_reservations.mapper.UserMapper;
import com.room_reservations.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roomreservations/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserDto> userDtos = userMapper.mapToUserDtoList(allUsers);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name) {
        List<User> userByNameList = userService.getUserByName(name);
        List<UserDto> userDtos = userMapper.mapToUserDtoList(userByNameList);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email) {
            List<User> userByEmail = userService.getUserByEmail(email);
            List<UserDto> userDtos = userMapper.mapToUserDtoList(userByEmail);
            return ResponseEntity.ok(userDtos);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = userMapper.mapToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/name/{name}")
    public ResponseEntity<Void> deleteUserByName(@PathVariable String name) {
        userService.deleteUserByName(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        User user = userService.updateUser(userDto);
        UserDto userDto1 = userMapper.mapToUserDto(user);
        return ResponseEntity.ok(userDto1);
    }

}
