package com.dreamcar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class represents car gearbox type in the app
 */
@Entity
@Setter @Getter
@NoArgsConstructor
@Table(name = "gear_boxes")
@ToString
public class Gearbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
