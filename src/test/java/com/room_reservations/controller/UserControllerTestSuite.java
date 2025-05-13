package com.room_reservations.controller;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import com.room_reservations.facade.RoomReservationFacade;
import com.room_reservations.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTestSuite {

    @Mock
    private RoomReservationFacade facade;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllUsers() {
        // Given
        List<User> users = List.of(new User());
        List<UserDto> userDtos = List.of(new UserDto());
        when(facade.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDtos, response.getBody());
    }

    @Test
    void shouldReturnUsersByName() {
        // Given
        String name = "John";
        List<User> users = List.of(new User());
        List<UserDto> userDtos = List.of(new UserDto());
        when(facade.getUsersByName(name)).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When
        ResponseEntity<List<UserDto>> response = userController.getUserByName(name);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDtos, response.getBody());
    }

    @Test
    void shouldReturnUsersByEmail() {
        // Given
        String email = "john@example.com";
        List<User> users = List.of(new User());
        List<UserDto> userDtos = List.of(new UserDto());
        when(facade.getUsersByEmail(email)).thenReturn(users);
        when(userMapper.mapToUserDtoList(users)).thenReturn(userDtos);

        // When
        ResponseEntity<List<UserDto>> response = userController.getUserByEmail(email);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDtos, response.getBody());
    }

    @Test
    void shouldDeleteUserByPassword() {
        // Given
        String password = "secret";

        // When
        ResponseEntity<Void> response = userController.deleteUserByPassword(password);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(facade).deleteUserByPassword(password);
    }

    @Test
    void shouldCreateUser() {
        // Given
        UserDto userDto = new UserDto();
        User user = new User();
        when(userMapper.mapToUser(userDto)).thenReturn(user);

        // When
        ResponseEntity<Void> response = userController.createUser(userDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(facade).saveUser(user);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        UserDto userDto = new UserDto();
        User updatedUser = new User();
        UserDto updatedDto = new UserDto();
        when(facade.updateUser(userDto)).thenReturn(updatedUser);
        when(userMapper.mapToUserDto(updatedUser)).thenReturn(updatedDto);

        // When
        ResponseEntity<UserDto> response = userController.updateUser(userDto);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedDto, response.getBody());
    }

}
