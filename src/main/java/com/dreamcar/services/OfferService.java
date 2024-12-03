package com.dreamcar.services;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.model.*;
import com.dreamcar.repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    FuelRepository fuelRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    GearboxRepository gearboxRepository;

    public void addNewOffer(OfferDTO offerDTO, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        OfferValidator.validateOffer(offerDTO);

        System.out.println(offerDTO);

        Offer offer = new Offer();
        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setMileage(offerDTO.getMileage());
        offer.setPrice(offerDTO.getPrice());
        offer.setYear(offerDTO.getYear());
        offer.setAdd_date(new Date());
        offer.setUser(user);
        offer.setFuel(this.fuelRepository.findById(offerDTO.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")));
        offer.setBrand(this.brandRepository.findById(offerDTO.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")));
        offer.setGearbox(this.gearboxRepository.findById(offerDTO.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id")));

        System.out.println(offer);

        this.offerRepository.save(offer);
    }

    public void editUserOffer(int offerId, OfferDTO offerDTO, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        OfferValidator.validateOffer(offerDTO);

        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("No offer with such id"));
        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setMileage(offerDTO.getMileage());
        offer.setPrice(offerDTO.getPrice());
        offer.setYear(offerDTO.getYear());

        offer.setUser(user);
        offer.setFuel(this.fuelRepository.findById(offerDTO.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")));
        offer.setBrand(this.brandRepository.findById(offerDTO.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")));
        offer.setGearbox(this.gearboxRepository.findById(offerDTO.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id")));;

        this.offerRepository.save(offer);
    }


    public void deleteUserOffer(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        // warunek uwzglednia 0 niespleniony nieprzepuszcza ALE gdy spelniony to tez
//        if(!user.getOffers().contains(offer)) throw new NoSuchElementException("You dont have offer with such id");

        Set<User> fav_offer_users = offer.getFavourite_by_users();
        for(User favUser : fav_offer_users) {
            favUser.getFavourites().remove(offer);
        }

        this.offerRepository.delete(offer);
    }

    public Set<Offer> getUserFavouriteOffers(HttpSession session) {
        User user = this.userService.getLoggedUser(session);

        return user.getFavourites();
    }

    public void addToFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        user.getFavourites().add(offer);
        // hibernate wie, ze user byl pobrany z bazy wiec go modyfikuje a nie zapisuje jako nowy
        this.userRepository.save(user);
    }

    public boolean checkIfOfferIsInFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        return user.getFavourites().contains(offer);
    }

    public void removeFromFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        if(!user.getFavourites().contains(offer)) throw new NoSuchElementException("You dont have favourite offer with such id");

        user.getFavourites().remove(offer);
        this.userRepository.save(user);
    }

    public List<?> getOptions(String getWhat, HttpSession session) {
        this.userService.getLoggedUser(session);

        switch(getWhat) {
            case "getBrands":
                return this.brandRepository.findAllByOrderByNameAsc();
            case "getFuels":
                return this.fuelRepository.findAllByOrderByNameAsc();
            case "getGearboxes":
                return this.gearboxRepository.findAllByOrderByNameAsc();
            default:
                 throw new NoSuchElementException();
        }
    }
}
