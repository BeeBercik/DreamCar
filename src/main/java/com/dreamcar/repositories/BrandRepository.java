package com.dreamcar.repositories;

import com.dreamcar.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByOrderByNameAsc();
}
