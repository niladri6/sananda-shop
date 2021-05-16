package com.sananda.inventory.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.VariantDTO;
import com.sananda.inventory.entity.Product;
import com.sananda.inventory.entity.Variants;
import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.repository.CategoryRepository;
import com.sananda.inventory.repository.ProductRepository;
import com.sananda.inventory.repository.VariantRepository;
import com.sananda.inventory.utils.Utils;

@Service
public class VariantServiceImpl implements VariantService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private VariantRepository variantRepo;

	@Override
	public List<Variants> getVariants() {
		return variantRepo.findAll();
	}

	@Override
	public List<Variants> getByProductId(long productId) {
		List<Variants> variants = variantRepo.findByProductId(productId);
		// System.out.println("Var: " + variants.toString());
		if (variants.isEmpty()) {
			throw new ResourceNotFoundException(String.format("Can not found with Id = %s", productId));
		}
		return variants;
	}

	@Override
	public Variants getById(long id) {
		Optional<Variants> variant = variantRepo.findById(id);
		if (!variant.isPresent()) {
			throw new ResourceNotFoundException(String.format("Variants not found with Id = %s", id));
		}
		return variantRepo.findById(id).get();
	}

	@Override
	public Variants save(Long productId, VariantDTO variantDTO) {
		Product product = productService.getById(productId);

		Variants variant = modelMapper.map(variantDTO, Variants.class);

		if (!Utils.isEmpty(variant)) {
			product.addVariant(variant); // for association
		}

		productRepo.save(product);

//		if (Utils.isEmpty(savedProduct)) {
//			throw new ResourceNotFoundException(Constants.NOT_CREATED_MSG);
//		}
		return variant;
	}

	/*
	 * update brand details and its categories also. user can add/remove categories.
	 */
	@Override
	public Variants update(long id, VariantDTO variantDTO) {
		Optional<Variants> variantOpt = variantRepo.findById(id);
		if (!variantOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Variants not found with Id = %s", id));
		}

		Variants variant = variantOpt.get();
		variant.setSku(variantDTO.getSku());
		variant.setMrp(variantDTO.getMrp());
		variant.setDiscount(variantDTO.getDiscount());
		variant.setSize(variantDTO.getSize());
		variant.setColour(variantDTO.getColour());

		Variants updatedVariants = variantRepo.save(variant);
		if (Utils.isEmpty(updatedVariants)) {
			throw new ResourceNotFoundException(Constants.NOT_UPDATED_MSG);
		}
		return updatedVariants;
	}

	@Override
	public Variants getBySku(String sku) {
		return variantRepo.findBySku(sku);
	}

	@Override
	public boolean saveOrUpdate(Variants variant) {
		variantRepo.save(variant);
		return true;
	}

	@Override
	public boolean delete(long id) {
		if (!variantRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Variants Not Exist with id = %s", id));
		}
		Variants variant = variantRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Variant Not Found"));

		System.out.println("Delete -> " + variant);
		variantRepo.delete(variant);

		return true;
	}

	@Override
	public boolean existsById(long id) {
		if (!variantRepo.existsById(id)) {
			throw new ResourceNotFoundException(String.format("Variant Not Found with id = %s", id));
		}
		return variantRepo.existsById(id);
	}

	@Override
	public boolean existsBySku(String sku) {
		return variantRepo.existsBySku(sku);
	}

}
