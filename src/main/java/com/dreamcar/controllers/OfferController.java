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

import java.util.*;

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
            if(userDTO == null)
                return ResponseEntity.badRequest().body("You are not logged in");
            this.offerService.deleteUserOffer(offerId, userDTO);

            return ResponseEntity.ok("Offer successfully deleted");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("addToFavourites/{id}")
    public ResponseEntity<?> addToFavourites(@PathVariable("id") int offerId, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO == null) return ResponseEntity.badRequest().body("You are not logged in");
        try {
            this.offerService.addToFavourites(offerId, userDTO);
            return ResponseEntity.ok("Offer successfully added to the favourites");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getFavouriteUserOffers")
    public ResponseEntity<?> getFavourites(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO == null) return ResponseEntity.badRequest().body("You are not logged in");
        try {
            Set<Offer> favourites = this.offerService.getUserFavouriteOffers(userDTO);
            return ResponseEntity.ok(favourites);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/isOfferInFavourites/{id}")
    public ResponseEntity<?> isOfferInFavourites(@PathVariable("id") int offerId, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO == null) return ResponseEntity.badRequest().body("You are not logged in");

        try {
            if(this.offerService.checkIfOfferIsInFavourites(offerId, userDTO))
                return ResponseEntity.ok(true);
            else throw new NoSuchElementException("Offer is not in favourites");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/removeFromFavourites/{id}")
    public ResponseEntity<?> removeFromFavourites(@PathVariable("id") int offerId, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO == null) return ResponseEntity.badRequest().body("You are not logged in");
        try {
            this.offerService.removeFromFavourites(offerId, userDTO);

            return ResponseEntity.ok("Offer successfully removed from the favourites");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
