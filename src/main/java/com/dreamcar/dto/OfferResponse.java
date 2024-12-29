package com.dreamcar.dto;

import com.dreamcar.model.Brand;
import com.dreamcar.model.Fuel;
import com.dreamcar.model.Gearbox;
import lombok.*;

import java.util.Date;

/**
 * Dto class represents server response with offer data
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OfferResponse {
    private int id;
    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Date add_date;
    /**
     * UserResponse has only necessary data without e.g. password
     */
    private UserResponse user;
    private Fuel fuel;
    private Brand brand;
    private Gearbox gearbox;
}
