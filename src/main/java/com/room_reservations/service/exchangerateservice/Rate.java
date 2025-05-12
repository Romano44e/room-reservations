package com.room_reservations.service.exchangerateservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Rate {

    @XmlElement(name = "No")
    private String no;

    @XmlElement(name = "EffectiveDate")
    private String effectiveDate;

    @Getter
    @XmlElement(name = "Mid")
    private Double mid;

}