package com.dreamcar.controllers;

import com.dreamcar.model.Offer;
import com.dreamcar.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferController {
    @Autowired
    OfferRepository offerRepository;

    @GetMapping("/allOffers")
    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    @GetMapping("/offerDetails/{id}")
    public Offer getOfferDetails(@PathVariable("id") int offerId) {
        return this.offerRepository.findById(offerId).get();
    }
}
