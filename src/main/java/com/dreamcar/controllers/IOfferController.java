package com.dreamcar.controllers;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOfferController {
    List<OfferResponse> getAllOffers();
    ResponseEntity<?> getUserOffers(HttpSession session);
    ResponseEntity<?> getOfferDetails(int offerId);
    ResponseEntity<?> addNewOffer(OfferRequest offerRequest, HttpSession session);
    ResponseEntity<?> editUserOffer(int offerId, OfferRequest offerRequest, HttpSession session);
    ResponseEntity<?> getOffer(int offerId);
    ResponseEntity<?> deleteOffer(int offerId, HttpSession session);
    ResponseEntity<?> addToFavourites(int offerId, HttpSession session);
    ResponseEntity<?> removeFromFavourites(int offerId, HttpSession session);
    ResponseEntity<?> getFavourites(HttpSession session);
    ResponseEntity<?> isOfferInFavourites(int offerId, HttpSession session);
    ResponseEntity<?> getOptions(String getWhat);
    ResponseEntity<List<OfferResponse>> applyFilters(FilterRequest filterRequest);
    ResponseEntity<List<OfferResponse>> getSortedOffers(String sortBy);
}
