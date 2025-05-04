package com.room_reservations.service.exchangerateservice;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "ExchangeRatesSeries")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRatesSeries {

    @XmlElement(name = "Table")
    private String table;

    @XmlElement(name = "Currency")
    private String currency;

    @XmlElement(name = "Code")
    private String code;

    @XmlElementWrapper(name = "Rates")
    @XmlElement(name = "Rate")
    private List<Rate> rates;

    public List<Rate> getRates() {
        return rates;
    }
}
