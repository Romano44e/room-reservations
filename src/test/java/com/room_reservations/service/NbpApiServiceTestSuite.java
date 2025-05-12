package com.room_reservations.service;

import com.room_reservations.service.exchangerateservice.NbpApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NbpApiServiceTestSuite {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NbpApiService nbpApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnExchangeRateFromNbpApi() {
        // Given
        String xml = """
            <ExchangeRatesSeries>
                <Rates>
                    <Rate>
                        <Mid>4.50</Mid>
                    </Rate>
                </Rates>
            </ExchangeRatesSeries>
        """;

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(xml));

        // When
        Double rate = nbpApiService.getEurExchangeRate();

        // Then
        assertNotNull(rate);
        assertEquals(4.50, rate);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenXmlParsingFails() {
        // Given
        String invalidXml = "<invalid></invalid>";
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(invalidXml));

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nbpApiService.getEurExchangeRate();
        });
        assertTrue(exception.getMessage().contains("Failed to parse NBP response"));
    }

}
