package com.dreamcar.controllers;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.Offer;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.services.OfferService;
import com.dreamcar.services.UserService;
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

    @Autowired
    UserService userService;

    @GetMapping("/allOffers")
    public List<OfferResponse> getAllOffers() {
        return this.offerService.getAllOffers();
    }

    @GetMapping("/getUserOffers")
    public ResponseEntity<?> getUserOffers(HttpSession session) {
        try {
            return ResponseEntity.ok(this.offerService.getUserOffers(session));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offerDetails/{id}")
    public ResponseEntity<?> getOfferDetails(@PathVariable("id") int offerId) {
        try {
            return ResponseEntity.ok(this.offerService.getOfferDetails(offerId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/addNewOffer")
    public ResponseEntity<?> addNewOffer(@RequestBody OfferRequest offerRequest, HttpSession session) {
        try {
            this.offerService.addNewOffer(offerRequest, session);
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
    public ResponseEntity<?> editUserOffer(@PathVariable("id") int offerId, @RequestBody OfferRequest offerRequest, HttpSession session) {
        try {
            this.offerService.editUserOffer(offerId, offerRequest, session);
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
        if(offer.isPresent())
            return ResponseEntity.ok(this.offerService.convertOfferToResponse(offer.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
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
            return ResponseEntity.ok(this.offerService.getUserFavouriteOffers(session));
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
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{getWhat}")
    public ResponseEntity<?> getOptions(@PathVariable("getWhat") String getWhat, HttpSession session) {
        try {
            return ResponseEntity.ok(this.offerService.getOptions(getWhat, session));
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/applyFilters")
    public ResponseEntity<List<OfferResponse>> applyFilters(@RequestBody FilterRequest filterRequest) {
        return ResponseEntity.ok(this.offerService.getFilteredOffers(filterRequest));
    }

    @GetMapping("/sortOffers")
    public ResponseEntity<List<OfferResponse>> getSortedOffers(@RequestParam(name = "sortBy", defaultValue = "recently_added") String sortBy) {
        return ResponseEntity.ok(this.offerService.getSortedOffers(sortBy));
    }
}
