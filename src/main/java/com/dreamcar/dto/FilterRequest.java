package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto class represents request from frontend with filter data.
 */
@Getter
@Setter
@ToString
public class FilterRequest {
    /**
     * Field with Integer type because brand type passed from html form has int type
     */
    private int brand;
    /**
     * Field with Integer type because fuel type passed from html form has int type
     */
    private int fuel;
    /**
     * Field with Integer type because gearbox type passed from html form has int type
     */
    private int gearbox;
    private int mileage_min;
    private int mileage_max;
    private int year_min;
    private int year_max;
    private int price_min;
    private int price_max;
}
