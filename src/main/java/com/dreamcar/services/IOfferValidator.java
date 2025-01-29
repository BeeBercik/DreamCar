package com.dreamcar.services;

import com.dreamcar.dto.OfferRequest;

public interface IOfferValidator {
    void validateOffer(OfferRequest offerRequest);
}
