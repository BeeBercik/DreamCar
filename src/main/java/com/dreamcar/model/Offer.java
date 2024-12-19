package com.dreamcar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter @Getter
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
@ToString
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private Integer mileage;
    private Integer year;
    private Integer price;
    private Date add_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "gearbox_id")
    private Gearbox gearbox;

    @ManyToMany(mappedBy = "favourites")
    @JsonIgnore
    @ToString.Exclude
    private Set<User> favourite_by_users = new HashSet<>();

    public Offer(String title, String description, Integer mileage, Integer year, Integer price, Date add_date, User user, Fuel fuel, Brand brand, Gearbox gearbox) {
        this.title = title;
        this.description = description;
        this.mileage = mileage;
        this.year = year;
        this.price = price;
        this.add_date = add_date;
        this.user = user;
        this.fuel = fuel;
        this.brand = brand;
        this.gearbox = gearbox;
    }
}
