package com.dreamcar.controllers;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.Brand;
import com.dreamcar.model.Fuel;
import com.dreamcar.model.Gearbox;
import com.dreamcar.model.Offer;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.services.OfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getOfferDetails(@PathVariable("id") int offerId) {
        try {
            Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
            return ResponseEntity.ok(offer);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/addNewOffer")
    public ResponseEntity<?> addNewOffer(@RequestBody OfferDTO offerDTO, HttpSession session) {
        try {
            this.offerService.addNewOffer(offerDTO, session);
            return ResponseEntity.ok("New offer added");
        } catch(UserNotLoggedInException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(IncorrectOfferDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/editUserOffer/{id}")
    public ResponseEntity<?> editUserOffer(@PathVariable("id") int offerId, @RequestBody OfferDTO offerDTO, HttpSession session) {
        try {
            this.offerService.editUserOffer(offerId, offerDTO, session);
            return ResponseEntity.ok("Offer edited");
        } catch(UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(IncorrectOfferDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getOffer/{id}")
    public ResponseEntity<?> getOffer(@PathVariable("id") int offerId) {
        Optional<Offer> offer = this.offerRepository.findById(offerId);
        if(offer.isPresent()) return ResponseEntity.ok(offer.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
    }

    @DeleteMapping("/deleteOffer/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable("id") int offerId, HttpSession session) {
        try {
            this.offerService.deleteUserOffer(offerId, session);
            return ResponseEntity.ok("Offer deleted");
        } catch(UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("addToFavourites/{id}")
    public ResponseEntity<?> addToFavourites(@PathVariable("id") int offerId, HttpSession session) {
        try {
            this.offerService.addToFavourites(offerId, session);
            return ResponseEntity.ok("Added to the favourites");
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/removeFromFavourites/{id}")
    public ResponseEntity<?> removeFromFavourites(@PathVariable("id") int offerId, HttpSession session) {
        try {
            this.offerService.removeFromFavourites(offerId, session);
            return ResponseEntity.ok("Removed from the favourites");
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getFavouriteUserOffers")
    public ResponseEntity<?> getFavourites(HttpSession session) {
        try {
            Set<Offer> favourites = this.offerService.getUserFavouriteOffers(session);

            return ResponseEntity.ok(favourites);
        } catch(UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/isOfferInFavourites/{id}")
    public ResponseEntity<?> isOfferInFavourites(@PathVariable("id") int offerId, HttpSession session) {
        try {
            if(this.offerService.checkIfOfferIsInFavourites(offerId, session))
                return ResponseEntity.ok(true);
            else throw new NoSuchElementException("Offer is not in favourites");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{getWhat}")
    public ResponseEntity<?> getOptions(@PathVariable("getWhat") String getWhat, HttpSession session) {
        try {
            return ResponseEntity.ok(this.offerService.getOptions(getWhat, session));
        } catch(UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
