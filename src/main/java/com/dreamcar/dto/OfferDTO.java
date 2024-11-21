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
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Date add_date;
    private Integer user;
    private Integer fuel;
    private Integer gearbox;
}
