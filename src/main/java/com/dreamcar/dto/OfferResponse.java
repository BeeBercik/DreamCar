package com.dreamcar.dto;

import com.dreamcar.model.Brand;
import com.dreamcar.model.Fuel;
import com.dreamcar.model.Gearbox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {
    private int id;
    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Date add_date;
    private UserResponse user;
    private Fuel fuel;
    private Brand brand;
    private Gearbox gearbox;
}
