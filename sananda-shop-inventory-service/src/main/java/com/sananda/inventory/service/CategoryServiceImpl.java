package com.sananda.inventory.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.CategoryDTO;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.exception.CustomException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.repository.CategoryRepository;
import com.sananda.inventory.utils.Utils;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public List<Category> getAll() {
		//List<Category> categorys = categoryRepo.findAll();
		return categoryRepo.findAll(); 
	}
	
	@Override
	public Page<Category> findAll(Pageable page) {
		return categoryRepo.findAll(page);
	}

	@Override
	public Category getById(long id) {
		Optional<Category> category = categoryRepo.findById(id);
		if (!category.isPresent()) {
			throw new ResourceNotFoundException(String.format("Category not found with Id = %s", id));
		}
		return categoryRepo.findById(id).get();
	}

	@Override
	public Category save(CategoryDTO categoryDTO) {
		// use modelMapper to convert DTO to Entity
		Category category = modelMapper.map(categoryDTO, Category.class);
		
		Category savedCategory = categoryRepo.save(category);
		if(savedCategory.getId() == null) {
			throw new ResourceNotFoundException(Constants.NOT_CREATED_MSG);
		}
		return savedCategory;
	}

	@Override
	public Category update(long id, CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		
		Optional<Category> categoryOpt = categoryRepo.findById(id);
		if (!categoryOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Category not found with Id = %s", id));
		}
		
		
		Category oldCategory = categoryOpt.get();
		oldCategory.setName(category.getName());
		oldCategory.setDescription(category.getDescription());
		//oldCategory.setBrands(category.getBrands());
		Category updatedCategory = categoryRepo.save(oldCategory);
		if(Utils.isEmpty(updatedCategory)) {
			throw new ResourceNotFoundException(Constants.NOT_UPDATED_MSG);
		}
		return updatedCategory;
	}

	@Override
	public Category getByName(String name) {
		Optional<Category> category = categoryRepo.findByName(name);
		if (!category.isPresent()) {
			throw new ResourceNotFoundException(String.format("Category Not found with Name = %s", name));
		}
		
		return category.get();
	}

	@Override
	public boolean saveOrUpdate(Category category) {
		//Category savedCategory = this.categoryRepo.save(category);
		categoryRepo.save(category);
		return true;
	}

	@Override
	public boolean delete(long id) {
		if (!categoryRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Category Not Exist with id = %s", id));
		}
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
		categoryRepo.delete(category);
		return true;
	}
}
