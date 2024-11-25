package com.dreamcar.services;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.dto.UserDTO;
import com.dreamcar.model.Offer;
import com.dreamcar.model.User;
import com.dreamcar.repositories.FuelRepository;
import com.dreamcar.repositories.GearboxRepository;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class OfferService {
    @Autowired
    OfferRepository offerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FuelRepository fuelRepository;

    @Autowired
    GearboxRepository gearboxRepository;

    public void addNewOffer(OfferDTO offerDTO) {
        OfferValidator.validateOffer(offerDTO);
        Offer offer = new Offer();

        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setBrand(offerDTO.getBrand());
        offer.setMileage(offerDTO.getMileage());
        offer.setPrice(offerDTO.getPrice());
        offer.setYear(offerDTO.getYear());
        offer.setAdd_date(new Date());

        offer.setUser(this.userRepository.findById(offerDTO.getUser()).get());
        offer.setFuel(this.fuelRepository.findById(offerDTO.getFuel()).get());
        offer.setGearbox(this.gearboxRepository.findById(offerDTO.getGearbox()).get());

        this.offerRepository.save(offer);
    }

    public void editUserOffer(int offerId, OfferDTO offerDTO) {
        OfferValidator.validateOffer(offerDTO);
        Offer offer = this.offerRepository.findById(offerId).get();

        offer.setTitle(offerDTO.getTitle());
        offer.setDescription(offerDTO.getDescription());
        offer.setBrand(offerDTO.getBrand());
        offer.setMileage(offerDTO.getMileage());
        offer.setPrice(offerDTO.getPrice());
        offer.setYear(offerDTO.getYear());

        offer.setUser(this.userRepository.findById(offerDTO.getUser()).get());
        offer.setFuel(this.fuelRepository.findById(offerDTO.getFuel()).get());
        offer.setGearbox(this.gearboxRepository.findById(offerDTO.getGearbox()).get());

        this.offerRepository.save(offer);
    }

    public void deleteUserOffer(int offerId, UserDTO userDTO) {
        User user = this.userRepository.findById(userDTO.getId()).get();
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        if(offer.getUser().getId() != user.getId()) {
            throw new NoSuchElementException("You dont have offer with such id");
        }

        Set<User> fav_offer_users = offer.getFavourite_by_users();
        for(User u : fav_offer_users) {
            u.getFavourites().remove(offer);
        }
        this.offerRepository.delete(offer);
    }

    public Set<Offer> getUserFavouriteOffers(UserDTO userDTO) {
        User user = this.userRepository.findById(userDTO.getId()).get();
        Set<Offer> favourites = user.getFavourites();
        if(favourites.isEmpty()) throw new NoSuchElementException("You dont have any favourite offer");

        return favourites;
    }

    public void addToFavourites(int offerId, UserDTO userDTO) {
        User user = this.userRepository.findById(userDTO.getId()).get();
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        user.getFavourites().add(offer);
        // hibernate wie, ze user byl pobrany z bazy wiec go modyfikuje a nie zapisuje jako nowy
        this.userRepository.save(user);
    }

    public boolean checkIfOfferIsInFavourites(int offerId, UserDTO userDTO) {
        User user = this.userRepository.findById(userDTO.getId()).get();
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

        return user.getFavourites().contains(offer);
    }

    public void removeFromFavourites(int offerId, UserDTO userDTO) {
        User user = this.userRepository.findById(userDTO.getId()).get();
        Offer offer = this.offerRepository.findById(offerId).get();

        if(!user.getFavourites().contains(offer)) throw new NoSuchElementException("You dont have favourite offer with such id");

        user.getFavourites().remove(offer);
        this.userRepository.save(user);
    }
}
