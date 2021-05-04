package com.sananda.inventory.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.ProductDTO;
import com.sananda.inventory.dto.VariantDTO;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.entity.Product;
import com.sananda.inventory.entity.Variants;
import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.repository.ProductRepository;
import com.sananda.inventory.utils.Utils;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private VariantService variantService;
	
	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}
	
	@Override
	public Page<Product> findAll(Pageable page) {
		return productRepo.findAll(page);
	}
	
	@Override
	public Product getById(long id) {
		Optional<Product> product = productRepo.findById(id);
		if (!product.isPresent()) {
			throw new ResourceNotFoundException(String.format("Product not found with Id = %s", id));
		}
		return productRepo.findById(id).get();
	}
	
	@Override
	public Product getByName(String name) {
		Optional<Product> product = productRepo.findByName(name);
		if (!product.isPresent()) {
			throw new ResourceNotFoundException(String.format("Product Not found with Name = %s", name));
		}
		
		return product.get();
	}
	
	@Override
	public Page<Product> getProductsByCategory(Category category, Pageable page) {
		return productRepo.findByCategory(category, page);
	}

	

	/*
	 * save product with different variant
	 * */
	@Override
	public Product save(ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		// category save
		Category category = categoryService.getById(productDTO.getCategoryId());
		product.setCategory(category);
		
		Set<Variants> variants = new HashSet<Variants>();
		// variant save
		for(VariantDTO variantDTO: productDTO.getVariants()) {
			//Variants savedVariant = variantService.save(variantDTO);
			Variants variant =  modelMapper.map(variantDTO, Variants.class);
			if(!Utils.isEmpty(variant)) {
				variant.setProduct(product); // satisfy two level association
				//product.getVariants().add(variant); // 1
				variants.add(variant); // 2
				//product.addVariant(variant); // for association
			}
		}
		product.setVariants(variants); //2
		if(product.getVariants().isEmpty()) {
			throw new InvalidDataException(Constants.PRODUCT_DONT_HAVE_VARIANT);
		}
		
		Product savedProduct = productRepo.save(product);
		if(Utils.isEmpty(savedProduct)) {
			throw new ResourceNotFoundException(Constants.NOT_CREATED_MSG);
		}
		return savedProduct;
	}

	@Override
	public Product update(long id, ProductDTO productDTO) {
		if(!productRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Product not Not Exists with Id = %s", id));
		}
		
		Optional<Product> productOpt = productRepo.findById(id);
		
		if (!productOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Product not found with Id = %s", id));
		}
		Product product = productOpt.get();
		product.setName(productDTO.getName());
		product.setShopImportDate(productDTO.getShopImportDate());
		product.setDistImportDate(productDTO.getDistImportDate());
		
		// category save
		Category category = categoryService.getById(productDTO.getCategoryId());
		product.setCategory(category);
		
		/*
		 * NEED MOD
		 * */
//		// variant save
//		for(VariantDTO variantDTO: productDTO.getVariants()) {
//			if(!variantService.existsById(variantDTO.getId())) {
//				Variants savedVariant = variantService.save(variantDTO);
//				if(!Utils.isEmpty(savedVariant)) {
//					product.getVariants().add(savedVariant);
//				}
//			}
//		}
		Set<Variants> variants = new HashSet<Variants>();
		for(VariantDTO variantDTO: productDTO.getVariants()) {
			Variants variant =  modelMapper.map(variantDTO, Variants.class);
			if(!Utils.isEmpty(variant)) {
				variant.setProduct(product); // satisfy two level association
				variants.add(variant);
			}
		}
		product.setVariants(variants);
		
		
		if(product.getVariants().isEmpty()) {
			throw new InvalidDataException(Constants.PRODUCT_DONT_HAVE_VARIANT);
		}
		
		Product savedProduct = productRepo.save(product);
		if(Utils.isEmpty(savedProduct)) {
			throw new ResourceNotFoundException(Constants.NOT_CREATED_MSG);
		}
		return savedProduct;
	}

	
	@Override
	public boolean delete(long id) {
		if (!productRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Product Not Exist with id = %s", id));
		}
		Product product = productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
		
		
		productRepo.delete(product);
		return true;
	}

	

	

	

	
}
