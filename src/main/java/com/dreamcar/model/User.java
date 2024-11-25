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
@ToString
@Table(name = "users")
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
    // a my nie chcemy mie takiego pola/kolumny  wtabeli - ta wlasciwosc sluzy do wskazania na relacje oraz operowanie na ofertach powiazaych z userem np getOffers
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Offer> offers = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            name = "favourite_offers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private Set<Offer> favourites = new HashSet<>();
}
