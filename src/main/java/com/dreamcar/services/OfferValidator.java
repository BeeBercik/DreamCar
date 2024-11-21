package com.dreamcar.services;

import com.dreamcar.dto.OfferDTO;

public class OfferValidator {
    static void validateNewOffer(OfferDTO offerDTO) {
//        title: title,
//                description: description,
//                brand: brand,
//                mileage: mileage,
//                year: year,
//                price: price,
//                user: user.id,
//                gearbox: gearbox,
//                fuel: fuel
        if(offerDTO.getTitle() == null || offerDTO.getTitle().trim().isEmpty() ||
        offerDTO.getDescription() == null || offerDTO.getDescription().trim().isEmpty() ||
        offerDTO.getBrand() == null || offerDTO.getBrand().trim().isEmpty() ||
        offerDTO.getMileage()  == null || offerDTO.getYear() == null ||
        offerDTO.getPrice() == null || offerDTO.getFuel() == null ||
        offerDTO.getGearbox() == null) {
            throw new IllegalArgumentException("Invalid data");
        }

        if(offerDTO.getYear() <= 1885) {
            throw new IllegalArgumentException("Year must be greater than 1885");
        }
        if(offerDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if(offerDTO.getTitle().length() < 5) {
            throw new IllegalArgumentException("Title must be at least 5 characters");
        }
    }
}
