package com.room_reservations;

import com.room_reservations.service.RandomwordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RoomReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomReservationsApplication.class, args);

		RandomwordService randomwordService = new RandomwordService(new RestTemplate());

		String s = randomwordService.generateRandomWord();
		System.out.println(s);

	}

}
