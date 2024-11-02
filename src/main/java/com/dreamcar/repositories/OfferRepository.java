package com.dreamcar.repositories;

import com.dreamcar.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
