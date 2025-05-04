package com.room_reservations.service.exchangerateservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Rate {

    @XmlElement(name = "No")
    private String no;

    @XmlElement(name = "EffectiveDate")
    private String effectiveDate;

    @XmlElement(name = "Mid")
    private Double mid;

    public Double getMid() {
        return mid;
    }
}