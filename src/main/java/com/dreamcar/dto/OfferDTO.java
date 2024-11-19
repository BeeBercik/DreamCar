package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OfferDTO {
    private String title;
    private String description;
    private String brand;
    private int mileage;
    private int year;
    private int price;
    private Date add_date;
    private int user;
    private int fuel;
    private int gearbox;
}
