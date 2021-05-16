package com.sananda.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sananda.inventory.dto.ProductDTO;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.entity.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Page<Product> findAll(Pageable page); // pagination
	Product getById(long id);
	boolean existsById(long id);
	Product getByName(String name);

	Page<Product> getProductsByCategory(Category category, Pageable page);
	
	Product save(ProductDTO productDTO);
	Product update(long id, ProductDTO productDTO);
	
	boolean delete(long id);
	

}
