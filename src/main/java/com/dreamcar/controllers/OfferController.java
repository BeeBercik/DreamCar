package com.dreamcar.controllers;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.dto.UserDTO;
import com.dreamcar.model.Fuel;
import com.dreamcar.model.Offer;
import com.dreamcar.model.User;
import com.dreamcar.repositories.FuelRepository;
import com.dreamcar.repositories.GearboxRepository;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.repositories.UserRepository;
import com.dreamcar.services.OfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OfferController {
    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

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
            this.offerService.addNewOffer(offerDTO);

            return ResponseEntity.ok("New offer added");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editUserOffer/{id}")
    public ResponseEntity<?> editUserOffer(@PathVariable("id") int offerId, @RequestBody OfferDTO offerDTO ) {
        try {
            this.offerService.editUserOffer(offerId, offerDTO);

            return ResponseEntity.ok("Offer successfully edited");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getOffer/{id}")
    public ResponseEntity<?> getOffer(@PathVariable("id") int offerId) {
        Optional<Offer> offer = this.offerRepository.findById(offerId);
        if(offer.isPresent()) return ResponseEntity.ok(offer.get());
        else return ResponseEntity.badRequest().body("Offer not found");
    }

    @GetMapping("/deleteOffer/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable("id") int offerId, HttpSession session) {
        try {
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            this.offerService.deleteUserOffer(offerId, userDTO);
            return ResponseEntity.ok("Offer successfully deleted");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
