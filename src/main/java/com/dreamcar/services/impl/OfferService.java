package com.dreamcar.services.impl;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.model.*;
import com.dreamcar.repositories.*;
import com.dreamcar.services.IOfferService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsible for business logic related to offers
 */
@RequiredArgsConstructor
@Service
public class OfferService implements IOfferService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final OfferRepository offerRepository;
    private final OfferValidator offerValidator;
    private final FuelRepository fuelRepository;
    private final BrandRepository brandRepository;
    private final GearboxRepository gearboxRepository;

    /**
     * Method look for all existing offers in repository
     *
     * @return list of offer dto objects sorted by date
     */
    public List<OfferResponse> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        offers.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

        return offers.stream()
                .map(this::convertOfferToResponse)
                .toList();
    }

    /**
     * Adds new offer to the database
     *
     * @param offerRequest given  offer data from html form
     * @param session used to check if user is logged in
     * @throws NoSuchElementException if there is not e.g car brand with such id in repository
     */
    public void addNewOffer(OfferRequest offerRequest, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        this.offerValidator.validateOffer(offerRequest);

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

    /**
     * Looks for logged user offers
     *
     * @param session used to check if user is logged in
     * @return list of dto offers object
     */
    public List<OfferResponse> getUserOffers(HttpSession session) {
        List<Offer> userOffers = this.userService.getLoggedUser(session).getOffers();
        userOffers.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

        return userOffers.stream()
                .map(this::convertOfferToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Method responsible for editing already existing offer data
     *
     * @param offerId id of offer to edit
     * @param offerRequest offer data passed from html form
     * @param session used to check if user who try to edit offer is logged in
     * @throws NoSuchElementException if there is not e.g car brand with such id in repository
     */
    public void editUserOffer(int offerId, OfferRequest offerRequest, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        this.offerValidator.validateOffer(offerRequest);

        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("No offer with such id"));
        offer.setTitle(offerRequest.getTitle());
        offer.setDescription(offerRequest.getDescription());
        offer.setMileage(offerRequest.getMileage());
        offer.setPrice(offerRequest.getPrice());
        offer.setYear(offerRequest.getYear());

        offer.setUser(user);
        offer.setFuel(this.fuelRepository.findById(offerRequest.getFuel()).orElseThrow(() -> new NoSuchElementException("No fuel with such id")));
        offer.setBrand(this.brandRepository.findById(offerRequest.getBrand()).orElseThrow(() -> new NoSuchElementException("No brand with such id")));
        offer.setGearbox(this.gearboxRepository.findById(offerRequest.getGearbox()).orElseThrow(() -> new NoSuchElementException("No gearbox with such id")));

        this.offerRepository.save(offer);
    }


    /**
     * Deletes offer in the app: remove from favourite users lists who added offer to their favourites and remove from user list who added that offer.
     *
     * @param offerId id of offer to delete
     * @param session used to check if user is logged in
     * @throws NoSuchElementException if offer with such id does not exist
     */
    public void deleteUserOffer(int offerId, HttpSession session) {
        this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        // warunek uwzglednia 0 niespleniony nieprzepuszcza ALE gdy spelniony to tez
//        if(!user.getOffers().contains(offer)) throw new NoSuchElementException("You dont have offer with such id");

        Set<User> fav_offer_users = offer.getFavourite_by_users();
        for(User favUser : fav_offer_users) {
            favUser.getFavourites().remove(offer);
        }

        this.offerRepository.delete(offer);
    }

    /**
     * Finds offer with given id and return it.
     *
     * @param offerId id of the offer to find
     * @return dto offer object
     * @throws NoSuchElementException if offer with such id odes not exit
     */
    public OfferResponse getOfferDetails(int offerId) {
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        return this.convertOfferToResponse(offer);
    }

    /**
     *  Method looks for user favourite offers
     *
     * @param session used to check if user is logged in
     * @return list of dto offer objects sorted by add date
     */
    public List<OfferResponse> getUserFavouriteOffers(HttpSession session) {
            User user = this.userService.getLoggedUser(session);
            List<Offer> userFavourites = user.getFavourites();
            userFavourites.sort((o1, o2) -> -o1.getAddDate().compareTo(o2.getAddDate()));

            return userFavourites.stream()
                    .map(this::convertOfferToResponse)
                    .collect(Collectors.toList());
    }

    /**
     * Method add offer to favourite user list
     *
     * @param offerId id of offer to add to favourites
     * @param session used to check if user is logged in
     * @throws NoSuchElementException if offer was not found
     */
    public void addToFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        user.getFavourites().add(offer);
        // hibernate wie, ze user byl pobrany z bazy - modyfikuje go
        this.userRepository.save(user);
    }

    /**
     * Checks if offer is already in logged user favourites
     *
     * @param offerId id of the offer to check
     * @param session used to get logged user
     * @return true if offer is in user favourite list, otherwise false
     */
    public boolean checkIfOfferIsInFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        return user.getFavourites().contains(offer);
    }

    /**
     * Method responsible for removing offer from user favourite list
     *
     * @param offerId id of the offer to remove from favourites
     * @param session used to get logged user
     * @throws NoSuchElementException if offer with such id was not found or user does not have that offer in the favourites
     */
    public void removeFromFavourites(int offerId, HttpSession session) {
        User user = this.userService.getLoggedUser(session);
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        if(!user.getFavourites().contains(offer)) throw new NoSuchElementException("You dont have favourite offer with such id");

        user.getFavourites().remove(offer);
        this.userRepository.save(user);
    }

    /**
     * Method looks for specific car properties like brands etc. and returns them.
     *
     * @param getWhat specific what is need to get form repository
     * @return list of specific car properties
     * @throws NoSuchElementException if specific car property does not exist in database
     */
    public List<?> getOptions(String getWhat) {
        return switch (getWhat) {
            case "getBrands" -> this.brandRepository.findAllByOrderByNameAsc();
            case "getFuels" -> this.fuelRepository.findAllByOrderByNameAsc();
            case "getGearboxes" -> this.gearboxRepository.findAllByOrderByNameAsc();
            default -> throw new NoSuchElementException();
        };
    }

    /**
     * Converts given offer to its dto class object
     *
     * @param offer to convert
     * @return converted dto object
     */
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

    /**
     * Method filters offers using given filterRequest with data from html form
     *
     * @param filterRequest sent request with user filters
     * @return filtered list off dto offer objects
     */
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

    /**
     * Sorts offer by specific order
     *
     * @param sortBy define how to sort offers
     * @return sorted list of dto offer objects
     */
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
