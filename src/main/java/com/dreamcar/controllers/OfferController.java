package com.dreamcar.controllers;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.Offer;
import com.dreamcar.model.User;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.services.OfferService;
import com.dreamcar.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        return this.offerRepository.findAllByOrderByAddDateDesc().stream()
                .map(this.offerService::convertOfferToResponse)
                .toList();
    }

    @GetMapping("/getUserOffers")
    public ResponseEntity<?> getUserOffers(HttpSession session) {
        try {
            User user = this.userService.getLoggedUser(session);

            return ResponseEntity.ok(user.getOffers().stream()
                            .map(offerService::convertOfferToResponse)
                            .collect(Collectors.toList()));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offerDetails/{id}")
    public ResponseEntity<?> getOfferDetails(@PathVariable("id") int offerId) {
        try {
            Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));

            return ResponseEntity.ok(this.offerService.convertOfferToResponse(offer));
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
    public ResponseEntity<?> applyFilters(@RequestBody FilterRequest filterRequest) {
        return ResponseEntity.ok(this.offerService.getFilteredOffers(filterRequest));
    }

    @GetMapping("/sortOffers")
    public ResponseEntity<?> getSortedOffers(@RequestParam(name = "sortBy", defaultValue = "recently_added") String sortBy) {
        return ResponseEntity.ok(this.offerService.getSortedOffers(sortBy).stream()
                .map(this.offerService::convertOfferToResponse)
                .toList());
    }
}
