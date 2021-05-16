package com.sananda.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sananda.inventory.entity.Variants;

@Repository
public interface VariantRepository extends JpaRepository<Variants, Long> {
	// for pagination
	// Page<Variants> findAll(Pageable page);

	// Native Query -> @Query(value = "SELECT * FROM variants WHERE product_id =
	// :product_id", nativeQuery = true)
	// @Query("SELECT v FROM variants v WHERE v.product_id = :product_id") // ORDER
	// BY v.created_at DESC")
	// @Query("SELECT v FROM variants v WHERE v.product_id = :product_id")
	@Query(value = "SELECT * FROM variants WHERE product_id = :product_id", nativeQuery = true)
	List<Variants> findByProductId(@Param("product_id") Long productId);

	Variants findBySku(String sku);

	Variants findBySize(String size);

	Variants findByColour(String colour);

	List<Variants> findByMrpBetween(int price1, int price2);

	boolean existsBySku(String sku);

}
