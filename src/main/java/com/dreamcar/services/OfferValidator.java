package com.dreamcar.services;

import com.dreamcar.dto.OfferDTO;
import com.dreamcar.exceptions.IncorrectOfferDataException;

public class OfferValidator {
    static void validateOffer(OfferDTO offerDTO) {
        if(offerDTO.getTitle() == null || offerDTO.getTitle().trim().isEmpty() ||
        offerDTO.getDescription() == null || offerDTO.getDescription().trim().isEmpty() ||
        offerDTO.getBrand() == null || offerDTO.getBrand().trim().isEmpty() ||
        offerDTO.getMileage()  == null || offerDTO.getYear() == null ||
        offerDTO.getPrice() == null || offerDTO.getFuel() == null ||
        offerDTO.getGearbox() == null) {
            throw new IncorrectOfferDataException("Invalid data");
        }

        if(offerDTO.getYear() <= 1885) {
            throw new IncorrectOfferDataException("Year must be greater than 1885");
        }
        if(offerDTO.getPrice() <= 0) {
            throw new IncorrectOfferDataException("Price must be greater than 0");
        }
        if(offerDTO.getTitle().length() < 5) {
            throw new IncorrectOfferDataException("Title must be at least 5 characters");
        }
    }
}
