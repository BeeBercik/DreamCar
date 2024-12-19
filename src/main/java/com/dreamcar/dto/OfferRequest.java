package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferRequest {
    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Integer fuel;
    private Integer brand;
    private Integer gearbox;
}
