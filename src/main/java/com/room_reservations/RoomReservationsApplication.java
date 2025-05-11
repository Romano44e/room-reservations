package com.room_reservations;

import com.room_reservations.service.RandomwordService;
import com.room_reservations.service.exchangerateservice.NbpApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class RoomReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomReservationsApplication.class, args);

		RandomwordService randomwordService = new RandomwordService(new RestTemplate());
		NbpApiService nbpApiService = new NbpApiService(new RestTemplate());

		String s = randomwordService.generateRandomWord();
		System.out.println(s);

		String s1 = randomwordService.generateRandomWord2();
		System.out.println(s1);

		Double eurExchangeRate = nbpApiService.getEurExchangeRate();
		System.out.println(eurExchangeRate);

	}

}
