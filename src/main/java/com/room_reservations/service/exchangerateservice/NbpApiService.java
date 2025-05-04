package com.room_reservations.service.exchangerateservice;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;

@Service
public class NbpApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public NbpApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getEurExchangeRate() {
        String url = "https://api.nbp.pl/api/exchangerates/rates/A/EUR/?format=xml";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            JAXBContext context = JAXBContext.newInstance(ExchangeRatesSeries.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(response.getBody());
            ExchangeRatesSeries series = (ExchangeRatesSeries) unmarshaller.unmarshal(reader);
            return series.getRates().get(0).getMid();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse NBP response", e);
        }
    }
}
