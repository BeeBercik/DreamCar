package com.dreamcar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * Class represents user in the app
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;
    private String password;
    private String email;
    private String phone;
    private Date add_date;

    /**
     * User offers list
     */
    @JsonIgnore // zapobiega powstania petli: w obj user sa oferty a w nich znow user, ktory je stworzyl itd
    @ToString.Exclude // to pole nie bedzie uwzglednione w metodzie toString()
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Offer> offers = new LinkedList<>();

    /**
     * Table with offer and user id
     */
    @ManyToMany
    @JoinTable(
            name = "favourite_offers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )

    /**
     * List of favourites user offers
     */
    @JsonIgnore
    @ToString.Exclude
    private List<Offer> favourites = new LinkedList<>();

    /**
     * Constructor creating user object
     *
     * @param login user login
     * @param password user password
     * @param email user email
     * @param phone user phone
     * @param add_date date when user was added
     */
    public User(String login, String password, String email, String phone, Date add_date) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.add_date = add_date;
    }
}
