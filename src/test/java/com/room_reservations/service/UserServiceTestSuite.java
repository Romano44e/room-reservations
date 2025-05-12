package com.room_reservations.service;

import com.room_reservations.domain.User;
import com.room_reservations.domain.UserDto;
import com.room_reservations.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTestSuite  {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RandomwordService randomwordService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveUserWithRandomPassword() {
        // Given
        User user = new User();
        when(randomwordService.generateRandomWord()).thenReturn("password123");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        User savedUser = userService.save(user);

        // Then
        assertEquals("password123", savedUser.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldFallbackToGenerateRandomWord2WhenFirstFails() {
        // Given
        User user = new User();
        when(randomwordService.generateRandomWord()).thenReturn("Failed to download random word");
        when(randomwordService.generateRandomWord2()).thenReturn("backupPass");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        User savedUser = userService.save(user);

        // Then
        assertEquals("backupPass", savedUser.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldGetUserByNameIgnoringCase() {
        // Given
        User u1 = new User(1L, "Alice", "a@email.com", 0, "pass");
        User u2 = new User(2L, "Bob", "b@email.com", 0, "pass");
        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        // When
        List<User> result = userService.getUserByName("ali");

        // Then
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void shouldGetUserByEmailIgnoringCase() {
        // Given
        User u1 = new User(1L, "Alice", "a@email.com", 0, "pass");
        User u2 = new User(2L, "Bob", "bob@gmail.com", 0, "pass");
        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        // When
        List<User> result = userService.getUserByEmail("gmail");

        // Then
        assertEquals(1, result.size());
        assertEquals("Bob", result.get(0).getName());
    }

    @Test
    void shouldDeleteUserByPassword() {
        // Given
        User user = new User(1L, "Name", "email", 0, "secret");
        when(userRepository.findAll()).thenReturn(List.of(user));

        // When
        userService.deleteUserByPassword("secret");

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldUpdateUserByPassword() {
        // Given
        User user = new User(1L, "Old", "old@mail.com", 10, "pass123");
        UserDto dto = new UserDto(1L, "New","new@mail.com", 99, "pass123");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        User updated = userService.updateUserByPassword(dto);

        // Then
        assertEquals("New", updated.getName());
        assertEquals("new@mail.com", updated.getEmail());
        assertEquals(99, updated.getPoints());
        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateUserPointsWhenCancelledById() {
        // Given
        User user = new User(1L, "Name", "email", 10, "pass");
        when(userRepository.findAll()).thenReturn(List.of(user));

        // When
        User updated = userService.updateUserPointsWhenCancelledById(1L);

        // Then
        assertEquals(30, updated.getPoints());
    }

}
