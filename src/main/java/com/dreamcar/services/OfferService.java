package com.dreamcar.services;

import com.dreamcar.dto.FilterRequest;
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

    public List<OfferResponse> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        offers.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

        return offers.stream()
                .map(this::convertOfferToResponse)
                .toList();
    }

    public void addNewOffer(OfferRequest offerRequest, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        OfferValidator.validateOffer(offerRequest);

        this.offerRepository.save(new Offer(
                offerRequest.getTitle(),
                offerRequest.getDescription(),
                offerRequest.getMileage(),
                offerRequest.getYear(),
                offerRequest.getPrice(),
                new Date(),
                user,
                this.fuelRepository.findById(offerRequest.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")),
                this.brandRepository.findById(offerRequest.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")),
                this.gearboxRepository.findById(offerRequest.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id"))
        ));
    }

    public List<OfferResponse> getUserOffers(HttpSession session) {
        List<Offer> userOffers = this.userService.getLoggedUser(session).getOffers();
        userOffers.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

        return userOffers.stream()
                .map(this::convertOfferToResponse)
                .collect(Collectors.toList());
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

    public OfferResponse getOfferDetails(int offerId) {
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        return this.convertOfferToResponse(offer);
    }

    public List<OfferResponse> getUserFavouriteOffers(HttpSession session) {
            User user = this.userService.getLoggedUser(session);
            List<Offer> userFavourites = user.getFavourites();
            userFavourites.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

            return userFavourites.stream()
                    .map(this::convertOfferToResponse)
                    .collect(Collectors.toList());
    }

    public void addToFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        user.getFavourites().add(offer);
        // hibernate wie, ze user byl pobrany z bazy - modyfikuje go
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
        return new OfferResponse(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getMileage(),
                offer.getYear(),
                offer.getPrice(),
                offer.getAddDate(),
                this.userService.convertUserToResponse(offer.getUser()),
                offer.getFuel(),
                offer.getBrand(),
                offer.getGearbox()
        );
    }

    public List<OfferResponse> getFilteredOffers(FilterRequest filterRequest) {
        return this.offerRepository.findAll().stream()
                .filter(o -> filterRequest.getBrand() == 0 ||
                        o.getBrand().getId() == filterRequest.getBrand())
                .filter(o -> filterRequest.getFuel() == 0 ||
                        o.getFuel().getId() == filterRequest.getFuel())
                .filter(o -> filterRequest.getGearbox() == 0 ||
                        o.getGearbox().getId() == filterRequest.getGearbox())
                .filter(o -> o.getMileage() >= filterRequest.getMileage_min())
                .filter(o -> filterRequest.getMileage_max() == 0 ||
                        o.getMileage() <= filterRequest.getMileage_max())
                .filter(o -> o.getPrice() >= filterRequest.getPrice_min())
                .filter(o -> filterRequest.getPrice_max() == 0 ||
                        o.getPrice() <= filterRequest.getPrice_max())
                .filter(o -> o.getYear() >= filterRequest.getYear_min())
                .filter(o -> filterRequest.getYear_max() == 0 ||
                        o.getYear() <= filterRequest.getYear_max())
                .map(this::convertOfferToResponse)
                .collect(Collectors.toList());
    }

    public List<OfferResponse> getSortedOffers(String sortBy) {
        List<Offer> offers = this.offerRepository.findAll();
        switch(sortBy) {
            case "price_asc":
                offers.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
                break;
            case "price_desc":
                offers.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
                break;
            default:
                offers.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));
                break;
        }

        return offers.stream().map(this::convertOfferToResponse).collect(Collectors.toList());
    }
}
