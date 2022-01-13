package com.sananda.inventory.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sananda.inventory.entity.Category;
import com.sananda.inventory.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// for pagination
	Page<Product> findAll(Pageable page);
	Optional<Product> findByName(String name);
	
	Page<Product> findByCategory(Category category, Pageable page);
	
	
	
//	List<ProductVariantResponse> getJoinInformation();

	
}
