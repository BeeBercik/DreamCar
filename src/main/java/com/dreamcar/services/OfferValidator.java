package com.dreamcar.services;

import com.dreamcar.dto.OfferRequest;
import com.dreamcar.exceptions.IncorrectOfferDataException;

public class OfferValidator {
    static void validateOffer(OfferRequest offerRequest) {
        if(offerRequest.getTitle() == null || offerRequest.getTitle().trim().isEmpty() ||
        offerRequest.getDescription() == null || offerRequest.getDescription().trim().isEmpty() ||
        offerRequest.getBrand() == null || offerRequest.getMileage()  == null || offerRequest.getYear() == null ||
        offerRequest.getPrice() == null || offerRequest.getFuel() == null || offerRequest.getGearbox() == null) {
            throw new IncorrectOfferDataException("Invalid data");
        }

        if(offerRequest.getYear() <= 1885) {
            throw new IncorrectOfferDataException("Year must be greater than 1885");
        }
        if(offerRequest.getPrice() <= 0) {
            throw new IncorrectOfferDataException("Price must be greater than 0");
        }
        if(offerRequest.getTitle().length() < 5) {
            throw new IncorrectOfferDataException("Title must be at least 5 characters");
        }
    }
}
