package com.dreamcar.repositories;

import com.dreamcar.model.Gearbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearboxRepository extends JpaRepository<Gearbox, Integer> {
    /**
     * Finds all gearbox types in order by name ascedning
     *
     * @return list of sorted gearbox types
     */
    List<Gearbox> findAllByOrderByNameAsc();
}
