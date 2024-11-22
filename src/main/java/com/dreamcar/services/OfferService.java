package com.dreamcar.services;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.model.Offer;
import com.dreamcar.repositories.FuelRepository;
import com.dreamcar.repositories.GearboxRepository;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
