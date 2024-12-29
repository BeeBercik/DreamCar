package com.dreamcar.repositories;

import com.dreamcar.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    /**
     * Finds all brands in order by name ascending
     *
     * @return list of sorted brands
     */
    List<Brand> findAllByOrderByNameAsc();
}
