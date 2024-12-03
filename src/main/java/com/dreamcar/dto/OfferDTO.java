package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class OfferDTO {
    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Date add_date;
    private Integer user;
    private Integer fuel;
    private Integer brand;
    private Integer gearbox;
}
