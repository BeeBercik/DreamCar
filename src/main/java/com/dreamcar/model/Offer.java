package com.dreamcar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter @Getter
@NoArgsConstructor
@ToString
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String brand;
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
    @JoinColumn(name = "gearbox_id")
    private GearBox gearbox;

    @ManyToMany(mappedBy = "favourites")
    private Set<User> favourite_by_users = new HashSet<>();
}
