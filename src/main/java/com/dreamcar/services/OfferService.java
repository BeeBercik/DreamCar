package com.dreamcar.services;

import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.model.*;
import com.dreamcar.repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public void addNewOffer(OfferRequest offerRequest, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        OfferValidator.validateOffer(offerRequest);

        Offer offer = new Offer();
        offer.setTitle(offerRequest.getTitle());
        offer.setDescription(offerRequest.getDescription());
        offer.setMileage(offerRequest.getMileage());
        offer.setPrice(offerRequest.getPrice());
        offer.setYear(offerRequest.getYear());
        offer.setAdd_date(new Date());
        offer.setUser(user);
        offer.setFuel(this.fuelRepository.findById(offerRequest.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")));
        offer.setBrand(this.brandRepository.findById(offerRequest.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")));
        offer.setGearbox(this.gearboxRepository.findById(offerRequest.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id")));

        this.offerRepository.save(offer);
    }

    public void editUserOffer(int offerId, OfferRequest offerRequest, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        OfferValidator.validateOffer(offerRequest);

        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("No offer with such id"));
        offer.setTitle(offerRequest.getTitle());
        offer.setDescription(offerRequest.getDescription());
        offer.setMileage(offerRequest.getMileage());
        offer.setPrice(offerRequest.getPrice());
        offer.setYear(offerRequest.getYear());

        offer.setUser(user);
        offer.setFuel(this.fuelRepository.findById(offerRequest.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")));
        offer.setBrand(this.brandRepository.findById(offerRequest.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")));
        offer.setGearbox(this.gearboxRepository.findById(offerRequest.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id")));;

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

    public Set<OfferResponse> getUserFavouriteOffers(HttpSession session) {
        User user = this.userService.getLoggedUser(session);

        return user.getFavourites().stream()
                .map(this::convertOfferToResponse)
                .collect(Collectors.toSet());
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
        return switch (getWhat) {
            case "getBrands" -> this.brandRepository.findAllByOrderByNameAsc();
            case "getFuels" -> this.fuelRepository.findAllByOrderByNameAsc();
            case "getGearboxes" -> this.gearboxRepository.findAllByOrderByNameAsc();
            default -> throw new NoSuchElementException();
        };
    }

    public OfferResponse convertOfferToResponse(Offer offer) {
        OfferResponse offerResponse = new OfferResponse();
        offerResponse.setId(offer.getId());
        offerResponse.setTitle(offer.getTitle());
        offerResponse.setDescription(offer.getDescription());
        offerResponse.setMileage(offer.getMileage());
        offerResponse.setPrice(offer.getPrice());
        offerResponse.setYear(offer.getYear());

        offerResponse.setUser(this.userService.convertUserToResponse(offer.getUser()));
        offerResponse.setFuel(offer.getFuel());
        offerResponse.setGearbox(offer.getGearbox());
        offerResponse.setBrand(offer.getBrand());

        return offerResponse;
    }
}
