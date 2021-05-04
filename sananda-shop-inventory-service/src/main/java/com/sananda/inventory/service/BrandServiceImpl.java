package com.sananda.inventory.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.BrandDTO;
import com.sananda.inventory.entity.Brand;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.exception.CustomException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.repository.BrandRepository;
import com.sananda.inventory.repository.CategoryRepository;
import com.sananda.inventory.utils.Utils;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BrandRepository brandRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<Brand> getBrands() {
		// List<Brand> brands = brandRepo.findAll();
		return brandRepo.findAll();
	}

	@Override
	public Page<Brand> findAll(Pageable page) {
		return brandRepo.findAll(page);
	}

	@Override
	public Brand getById(long id) {
		Optional<Brand> brand = brandRepo.findById(id);
		if (!brand.isPresent()) {
			throw new ResourceNotFoundException(String.format("Brand not found with Id = %s", id));
		}
		return brandRepo.findById(id).get();
	}

	@Override
	public Brand save(BrandDTO brandDTO) {
//		Brand brand = new Brand();
//		brand.setName(brandDTO.getName());
//		brand.setDescription(brandDTO.getDescription());

		Brand brand = modelMapper.map(brandDTO, Brand.class);

		// use rest template
		Set<Long> categoryId = brandDTO.getCategories();
		Set<Category> categories = new HashSet<Category>();
		try {
			for (long cId : categoryId) {
				Category category = null;
				category = categoryService.getById(cId);
				if (category != null) {
					// brand.getCategories().add(category);
					categories.add(category);
				}

//			Optional<Category> category = categoryRepo.findById((long) cId);
//			if(category.isPresent()) {
//				categories.add(category.get());
//			}

			}
		} catch (ResourceNotFoundException e) {
			e.getMessage();
		}

		brand.setCategories(categories);

		Brand savedBrand = brandRepo.save(brand);
		if (savedBrand.getId() == null) {
			throw new CustomException(Constants.NOT_CREATED_MSG);
		}
		return savedBrand;
		// return brandRepo.save(brand);
	}

	/*
	 * update brand details and its categories also. user can add/remove categories.
	 */
	@Override
	public Brand update(long id, BrandDTO brandDTO) {
		Optional<Brand> brandOpt = brandRepo.findById(id);
		if (!brandOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Brand not found with Id = %s", id));
		}

		Brand brand = brandOpt.get();
		brand.setName(brandDTO.getName());
		brand.setDescription(brandDTO.getDescription());

		// remove all the categories
		brand.getCategories().clear();

		// add all fresh categories.
		// try {
		for (long catId : brandDTO.getCategories()) {
			Optional<Category> categoryOpt = categoryRepo.findById(catId);
			if (categoryOpt.isPresent()) {
				brand.getCategories().add(categoryOpt.get());
			}
		}
//		} catch(ResourceNotFoundException e) {
//			e.getMessage();
//		}

		Brand updatedBrand = brandRepo.save(brand);
		if (Utils.isEmpty(updatedBrand)) {
			throw new ResourceNotFoundException(Constants.NOT_UPDATED_MSG);
		}
		return updatedBrand;
	}

	@Override
	public Brand getByName(String name) {
		return brandRepo.findByName(name);
	}

	@Override
	public boolean saveOrUpdate(Brand brand) {
		// Brand savedBrand = this.brandRepo.save(brand);
		brandRepo.save(brand);
		return true;
	}

	@Override
	public boolean delete(long id) {
		if (!brandRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Brand Not Exist with id = %s", id));
		}
		Brand brand = brandRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand Not Found"));
		brandRepo.delete(brand);
		return true;
	}

}
