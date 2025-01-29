package com.dreamcar.controllers.impl;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import com.dreamcar.model.Offer;
import com.dreamcar.repositories.OfferRepository;
import com.dreamcar.services.impl.OfferService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Rest controller that handles requests related to offers from frontend and generates responses
 */
@RestController
@RequestMapping("/api")
public class IOfferController implements com.dreamcar.controllers.IOfferController {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

    /**
     * Endpoint which return all existing offers
     *
     * @return all existing offers
     */
    @GetMapping("/allOffers")
    public List<OfferResponse> getAllOffers() {
        return this.offerService.getAllOffers();
    }

    /**
     * Return all user offers
     *
     * @param session used to get user and his offers
     * @return response object with all user offers, otherwise response 400 code status with message what was wrong
     */
    @GetMapping("/getUserOffers")
    public ResponseEntity<?> getUserOffers(HttpSession session) {
        try {
            return ResponseEntity.ok(this.offerService.getUserOffers(session));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint returns offer with specific id with itd details
     *
     * @param offerId id of offer to return
     * @return response object with offer with such id, otherwise response with 404 code status with message what was wrong
     */
    @GetMapping("/offerDetails/{id}")
    public ResponseEntity<?> getOfferDetails(@PathVariable("id") int offerId) {
        try {
            return ResponseEntity.ok(this.offerService.getOfferDetails(offerId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint adds new user offer
     *
     * @param offerRequest it contains offer details data
     * @param session used to get logged user who try to add new offer
     * @return response object with 200 code status and message if everything was correct, otherwise message with specific code status what was wrong
     */
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

    /**
     * Endpoint responsible for editing offer with specific id
     *
     * @param offerId id of the offer to edit
     * @param offerRequest contains offer details data
     * @param session used to get user and check if user is logged in
     * @return response object with 200 code status and message if everything was correct, otherwise message with specific code status what was wrong
     */
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

    /**
     * Endpoint returns offer with specific id with its details
     *
     * @param offerId id of the offer to return
     * @return dto offer object with specific id or message with 404 code status is offer was not found
     */
    @GetMapping("/getOffer/{id}")
    public ResponseEntity<?> getOffer(@PathVariable("id") int offerId) {
        Optional<Offer> offer = this.offerRepository.findById(offerId);
        if(offer.isPresent())
            return ResponseEntity.ok(this.offerService.convertOfferToResponse(offer.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
    }

    /**
     * Endpoint responsible for deleting offer
     *
     * @param offerId id of the offer to delete
     * @param session used to verify logged user
     * @return response object with 200 code status if everything was fine, 401 if user was not logged in, 400 if there was not offer with such id
     */
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

    /**
     * Endpoint adds offer to user favourites
     *
     * @param offerId id of the offer to add to favourites
     * @param session to verify logged in user
     * @return response object with 200 status if added successfully, otherwise 401 if user was not logged in or 404 if offer with such id was not found`
     */
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

    /**
     * Endpoint responsible for removing offer from user favourites
     *
     * @param offerId id of the offer to remove
     * @param session used to to verify if user is logged in
     * @return @return response object with 200 status if added successfully, otherwise 401 if user was not logged in or 404 if offer with such id was not found
     */
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

    /**
     * Endpoint returns favourites offers for logged user
     *
     * @param session used to verify iif user is logged in
     * @return @return response object with 200 status if added successfully, otherwise 401 if user was not logged in
     */
    @GetMapping("/getFavouriteUserOffers")
    public ResponseEntity<?> getFavourites(HttpSession session) {
        try {
            return ResponseEntity.ok(this.offerService.getUserFavouriteOffers(session));
        } catch(UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Endpoint checks if offer is already in favourites list
     *
     * @param offerId id of the offer to check
     * @param session used to get logged user
     * @return @return response object with 200 status if added successfully, otherwise 401 if user was not logged in or 404 if offer with such id is not in favourites list
     */
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

    /**
     * Endpoint responsible for returning specific offer elements like brands, etc.
     *
     * @param getWhat specify what is need to get
     * @return @return response object with 200 status and list of specified elements, otherwise 404 if offer does not have such elements
     */
    @GetMapping("/{getWhat}")
    public ResponseEntity<?> getOptions(@PathVariable("getWhat") String getWhat) {
        try {
            return ResponseEntity.ok(this.offerService.getOptions(getWhat));
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint responsible for applying filters and returning filtered offers
     *
     * @param filterRequest contains filters data
     * @return list with filtered offers
     */
    @PostMapping("/applyFilters")
    public ResponseEntity<List<OfferResponse>> applyFilters(@RequestBody FilterRequest filterRequest) {
        return ResponseEntity.ok(this.offerService.getFilteredOffers(filterRequest));
    }

    /**
     * Endpoint responsible for sorting offers in specified way
     *
     * @param sortBy it says how to sort offers
     * @return list of sorted offers
     */
    @GetMapping("/sortOffers")
    public ResponseEntity<List<OfferResponse>> getSortedOffers(@RequestParam(name = "sortBy", defaultValue = "recently_added") String sortBy) {
        return ResponseEntity.ok(this.offerService.getSortedOffers(sortBy));
    }
}
