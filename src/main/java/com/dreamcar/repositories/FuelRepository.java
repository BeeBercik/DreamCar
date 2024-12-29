package com.dreamcar.repositories;

import com.dreamcar.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    /**
     * Finds all fuel types in order by name ascedning
     *
     * @return list of sorted fuel types
     */
    List<Fuel> findAllByOrderByNameAsc();
}
