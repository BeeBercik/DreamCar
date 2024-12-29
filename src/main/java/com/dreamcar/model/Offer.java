package com.dreamcar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents offer in the app
 */
@Entity
@Setter @Getter
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

    @Column(name = "add_date")
    private Date addDate;

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

    /**
     * Set of users who added the offer to favourite list
     */
    @ManyToMany(mappedBy = "favourites")
    @JsonIgnore
    @ToString.Exclude
    private Set<User> favourite_by_users = new HashSet<>();

    /**
     * Constructor creating offer object
     *
     * @param title  title of the offer
     * @param description description of the offer
     * @param mileage mileage of the car in km
     * @param year year of car production
     * @param price price of the car in pln
     * @param add_date date when offer was added
     * @param user user who created the offer
     * @param fuel fuel type of the car
     * @param brand brand of the car
     * @param gearbox gearbox type of the car
     */
    public Offer(String title, String description, Integer mileage, Integer year, Integer price, Date add_date, User user, Fuel fuel, Brand brand, Gearbox gearbox) {
        this.title = title;
        this.description = description;
        this.mileage = mileage;
        this.year = year;
        this.price = price;
        this.addDate = add_date;
        this.user = user;
        this.fuel = fuel;
        this.brand = brand;
        this.gearbox = gearbox;
    }
}
