package com.dreamcar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

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

    // zapobiega serializacji bo JSJON mapuje na encje Usera i probuje polaczyc z nim jego oferty, aby np. wyswietlich ich dane w widoku
    // json nie zawiera tego pola offers w przekazywanym obiekcie na frontend
    @JsonIgnore
    @ToString.Exclude // to pole nie bedzie uwzglednione w metodzie toString()
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Offer> offers = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            name = "favourite_offers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )

    @JsonIgnore
    @ToString.Exclude
    private Set<Offer> favourites = new HashSet<>();

    public User(String login, String password, String email, String phone, Date add_date) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.add_date = add_date;
    }
}
