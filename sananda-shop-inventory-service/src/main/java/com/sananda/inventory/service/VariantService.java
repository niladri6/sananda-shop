package com.sananda.inventory.service;

import java.util.List;

import com.sananda.inventory.dto.VariantDTO;
import com.sananda.inventory.entity.Variants;

public interface VariantService {
	List<Variants> getVariants();
	Variants getById(long id);
	Variants getByCode(String code);
	
	Variants save(VariantDTO variantDTO);
	//Variants update(long id, VariantDTO variantDTO);
	
	Variants update(long id, VariantDTO variantDTO);
	
	boolean saveOrUpdate(Variants variant);
	//boolean saveOrUpdate(VariantDTO variantDTO);
	
	boolean delete(long id);
	
	boolean existsById(long id);
	boolean existsByCode(String code);
	
}
