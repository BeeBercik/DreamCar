package com.dreamcar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto class represents request from frontend with offer data.
 */
@Getter
@Setter
@ToString
public class OfferRequest {
    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    /**
     * Field with Integer type because fuel type passed from html form has int type
     */
    private Integer fuel;
    /**
     * Field with Integer type because brand type passed from html form has int type
     */
    private Integer brand;
    /**
     * Field with Integer type because gearbox type passed from html form has int type
     */
    private Integer gearbox;
}
