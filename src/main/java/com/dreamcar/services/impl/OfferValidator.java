package com.dreamcar.services.impl;

import com.dreamcar.dto.OfferRequest;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.services.IOfferValidator;
import org.springframework.stereotype.Service;

/**
 * Class responsible for validate given offer data
 */
@Service
public class OfferValidator implements IOfferValidator {
    /**
     * Validates provided by user data with specific requirements
     *
     * @param offerRequest offer data provided from html form
     * @throws IncorrectOfferDataException if any of the condition is not met
     */
     public void validateOffer(OfferRequest offerRequest) {
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
