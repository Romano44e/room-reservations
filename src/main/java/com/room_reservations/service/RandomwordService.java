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

    public String generateRoomName() {

        String url = "https://random-word-api.herokuapp.com/word";

        try {
            ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
            String[] word = response.getBody();
            String word1 = word.toString();
            log.info("fetched word {}", word1);
            if (word1 != null) {
                return word1 + " Room";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "Unnamed Room";
    }

}