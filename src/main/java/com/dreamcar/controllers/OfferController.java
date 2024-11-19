package com.dreamcar.controllers;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.model.Fuel;
import com.dreamcar.model.Offer;
import com.dreamcar.repositories.FuelRepository;
import com.dreamcar.repositories.GearboxRepository;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.repositories.UserRepository;
import com.dreamcar.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferController {
    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FuelRepository fuelRepository;

    @Autowired
    GearboxRepository gearboxRepository;

    @GetMapping("/allOffers")
    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    @GetMapping("/offerDetails/{id}")
    public Offer getOfferDetails(@PathVariable("id") int offerId) {
        return this.offerRepository.findById(offerId).get();
    }

    @PostMapping("/addNewOffer")
    public ResponseEntity<?> addNewOffer(@RequestBody OfferDTO offerDTO) {
        try {
//            validate new offer form data
//            z js zwracamy np fule jako int id, a mapuje na offer gdzie fuel to obiekt
//            z formualrza zwracalo fuel itd name zamiast id
//            daj logike do service i stworz validate dla pol ;)
            Offer offer = new Offer();
            offer.setTitle(offerDTO.getTitle());
            offer.setDescription(offerDTO.getDescription());
            offer.setBrand(offerDTO.getBrand());
            offer.setMileage(offerDTO.getMileage());
            offer.setPrice(offerDTO.getPrice());
            offer.setAdd_date(new Date());

            offer.setUser(this.userRepository.findById(offerDTO.getUser()).get());
            offer.setFuel(this.fuelRepository.findById(offerDTO.getFuel()).get());
            offer.setGearbox(this.gearboxRepository.findById(offerDTO.getGearbox()).get());

            this.offerRepository.save(offer);
            return ResponseEntity.ok("New offer added");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
