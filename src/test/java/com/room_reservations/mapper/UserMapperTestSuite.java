package com.room_reservations.mapper;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTestSuite {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void shouldMapUserDtoToUser() {
        // Given
        UserDto dto = new UserDto(1L, "John", "john@example.com", 100, "secret");

        // When
        User user = userMapper.mapToUser(dto);

        // Then
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPoints(), user.getPoints());
        assertEquals(dto.getPassword(), user.getPassword());
    }

    @Test
    void shouldMapUserToUserDto() {
        // Given
        User user = new User(2L, "Jane", "jane@example.com", 200, "pwd123");

        // When
        UserDto dto = userMapper.mapToUserDto(user);

        // Then
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPoints(), dto.getPoints());
        assertEquals(user.getPassword(), dto.getPassword());
    }

    @Test
    void shouldMapUserListToUserDtoList() {
        // Given
        List<User> users = List.of(
                new User(1L, "A", "a@mail.com", 10, "pass1"),
                new User(2L, "B", "b@mail.com", 20, "pass2")
        );

        // When
        List<UserDto> dtos = userMapper.mapToUserDtoList(users);

        // Then
        assertEquals(2, dtos.size());
        assertEquals("A", dtos.get(0).getName());
        assertEquals("B", dtos.get(1).getName());
    }

}
