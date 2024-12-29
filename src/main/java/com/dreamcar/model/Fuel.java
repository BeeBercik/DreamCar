package com.dreamcar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class represents car fuel type in the app
 */
@Entity
@Setter @Getter
@NoArgsConstructor
@Table(name = "fuels")
@ToString
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}

