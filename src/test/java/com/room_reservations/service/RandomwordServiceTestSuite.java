package com.room_reservations.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RandomwordServiceTestSuite {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RandomwordService randomwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnRandomWordFromApi() {
        // Given
        String[] words = {"example"};
        when(restTemplate.getForEntity(anyString(), eq(String[].class)))
                .thenReturn(ResponseEntity.ok(words));

        // When
        String result = randomwordService.generateRandomWord();

        // Then
        assertEquals("example", result);
    }

    @Test
    void shouldReturnFallbackMessageWhenApiFails() {
        // Given
        when(restTemplate.getForEntity(anyString(), eq(String[].class)))
                .thenThrow(new RuntimeException("API failure"));

        // When
        String result = randomwordService.generateRandomWord();

        // Then
        assertEquals("Failed to download random word", result);
    }

    @Test
    void shouldGenerateRandomWord2WithinLengthRange() {
        // When
        String result = randomwordService.generateRandomWord2();

        // Then
        assertNotNull(result);
        assertTrue(result.length() >= 3 && result.length() <= 9);
        assertTrue(result.matches("[a-z]+"));
    }
}
