package com.dreamcar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {
    private String brand;
    private int fuel;
    private int mileage_min;
    private int mileage_max;
    private int year_min;
    private int year_max;
    private int gearbox;
    private int price_min;
    private int price_max;
}
