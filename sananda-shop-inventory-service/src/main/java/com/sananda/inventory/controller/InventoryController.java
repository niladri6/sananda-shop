package com.sananda.inventory.controller;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.inventory.entity.Brand;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.entity.Product;
import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.response.ApiResponse;
import com.sananda.inventory.response.MenuItems;
import com.sananda.inventory.service.BrandService;
import com.sananda.inventory.service.CategoryService;
import com.sananda.inventory.service.ProductService;
import com.sananda.inventory.service.VariantService;
import com.sananda.inventory.utils.Utils;

@RestController
@RequestMapping(value = "/api")
public class InventoryController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private VariantService variantService;

	@GetMapping("/brand-menu")
	public ResponseEntity<?> getBrands() {
		ApiResponse response = new ApiResponse();
		List<Brand> brands = brandService.getBrands();

		List<MenuItems> itemList = new ArrayList<MenuItems>();
		for (Brand brand : brands) {
			MenuItems item = new MenuItems();
			item.setId(brand.getId());
			item.setName(brand.getName());
			itemList.add(item);
		}

		if (itemList.isEmpty()) {
			throw new ResourceNotFoundException("Brand not found");
		}
		Utils.menuItemSort(itemList);
		response.setSuccess(Boolean.TRUE);
		response.setData(itemList);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/brand-category-menu")
	public ResponseEntity<?> getBrandCategory(@RequestBody Map<String, Long> param) {

		// experiment
		if (Utils.isKeyValueNull(param, "brand_id")) {
			throw new InvalidDataException("brand_id is required");
		}

		Long brandId = param.get("brand_id");

		Set<Category> categories = brandService.getById(brandId).getCategories();

		ApiResponse response = new ApiResponse();
		// List<Brand> brands = categoryService.getBrands();

		List<MenuItems> itemList = new ArrayList<MenuItems>();
		for (Category category : categories) {
			MenuItems item = new MenuItems();
			item.setId(category.getId());
			item.setName(category.getName());
			itemList.add(item);
		}

		if (itemList.isEmpty()) {
			throw new ResolutionException("Category not found");
		}

		Utils.menuItemSort(itemList);

		response.setSuccess(Boolean.TRUE);
		response.setData(itemList);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/brand-category-products")
	public ResponseEntity<?> getProductByCategory(@RequestBody Map<String, Long> param) {
		// experiment
		if (Utils.isKeyValueNull(param, "category_id")) {
			throw new InvalidDataException("category_id is required");
		}

		Long categoryId = param.get("category_id");

		List<Product> products = categoryService.getById(categoryId).getProducts();
		
		ApiResponse response = new ApiResponse();
		
		response.setSuccess(Boolean.TRUE);
		response.setData(products);
		return ResponseEntity.ok().body(response);

	}

}
