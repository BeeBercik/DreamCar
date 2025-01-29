package com.dreamcar.services;

import com.dreamcar.dto.FilterRequest;
import com.dreamcar.dto.OfferRequest;
import com.dreamcar.dto.OfferResponse;
import com.dreamcar.model.Offer;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IOfferService {
    List<OfferResponse> getAllOffers();
    void addNewOffer(OfferRequest offerRequest, HttpSession session);
    List<OfferResponse> getUserOffers(HttpSession session);
    void editUserOffer(int offerId, OfferRequest offerRequest, HttpSession session);
    void deleteUserOffer(int offerId, HttpSession session);
    OfferResponse getOfferDetails(int offerId);
    List<OfferResponse> getUserFavouriteOffers(HttpSession session);
    void addToFavourites(int offerId, HttpSession session);
    boolean checkIfOfferIsInFavourites(int offerId, HttpSession session);
    void removeFromFavourites(int offerId, HttpSession session);
    List<?> getOptions(String getWhat);
    OfferResponse convertOfferToResponse(Offer offer);
    List<OfferResponse> getFilteredOffers(FilterRequest filterRequest);
    List<OfferResponse> getSortedOffers(String sortBy);
}
