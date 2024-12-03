package com.dreamcar.repositories;

import com.dreamcar.model.Gearbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearboxRepository extends JpaRepository<Gearbox, Integer> {
    List<Gearbox> findAllByOrderByNameAsc();
}
