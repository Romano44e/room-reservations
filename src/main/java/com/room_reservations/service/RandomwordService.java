package com.room_reservations.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class RandomwordService {

    private final RestTemplate restTemplate;

    public String generateRandomWord() {
        String url = "https://random-word-api.herokuapp.com/word";

        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
            String[] words = response.getBody();
            if (words != null && words.length > 0) {
                String word = words[0];
                log.info("Fetched word: {}", word);
                return word;
            }
        } catch (Exception e) {
            log.error("Failed to fetch word: {}", e.getMessage());
        }

        return "Failed to download random word";
    }

}