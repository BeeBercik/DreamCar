package com.dreamcar.repositories;

import com.dreamcar.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findAllByOrderByPriceDesc();
    List<Offer> findAllByOrderByPriceAsc();
    List<Offer> findAllByOrderByAddDateDesc();
}
