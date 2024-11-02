package com.dreamcar.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
    private int mileage;
    private int year;
    private int price;
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
}
