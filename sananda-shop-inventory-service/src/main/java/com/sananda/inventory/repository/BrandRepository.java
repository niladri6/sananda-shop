package com.sananda.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sananda.inventory.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	// for pagination
	Page<Brand> findAll(Pageable p);
	Brand findByName(String name);
	
	
	

}
