package com.sananda.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sananda.inventory.dto.CategoryDTO;
import com.sananda.inventory.entity.Category;

public interface CategoryService {
	List<Category> getAll();
	Page<Category> findAll(Pageable page); // pagination
	Category getById(long id);
	Category getByName(String name);
	//Optional<Category> getByName(String name);
	
	boolean saveOrUpdate(Category category);
	Category save(CategoryDTO categoryDTO);
	Category update(long id, CategoryDTO categoryDTO);
	boolean delete(long id);
	
//	CategoryDTO convertToDto(Category category);
//	Category convertToEntity(CategoryDTO CategoryDTO);
	

}
