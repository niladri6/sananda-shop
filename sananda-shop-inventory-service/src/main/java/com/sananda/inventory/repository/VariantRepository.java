package com.sananda.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sananda.inventory.entity.Variants;

@Repository
public interface VariantRepository extends JpaRepository<Variants, Long> {
	// for pagination
	//Page<Variants> findAll(Pageable page);
	Variants findByCode(String code);
	Variants findBySize(String size);
	Variants findByColour(String colour);
	List<Variants> findByMrpBetween(int price1, int price2);
	
	boolean existsByCode(String code);
	
}
