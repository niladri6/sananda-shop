package com.sananda.inventory.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sananda.inventory.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	// for pagination
	Page<Category> findAll(Pageable page);
		
	//Category findByName(String name);
	Optional<Category> findByName(String name);

}
