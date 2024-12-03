package com.dreamcar.repositories;

import com.dreamcar.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    List<Fuel> findAllByOrderByNameAsc();
}
