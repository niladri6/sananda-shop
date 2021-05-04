package com.sananda.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sananda.inventory.dto.BrandDTO;
import com.sananda.inventory.entity.Brand;

public interface BrandService {
	List<Brand> getBrands();
	Page<Brand> findAll(Pageable page); // pagination
	Brand getById(long id);
	Brand getByName(String name);
	
	//Brand save(Brand brand); //BrandDTO
	Brand save(BrandDTO brandDTO);
	Brand update(long id, BrandDTO brandDTO);
//	boolean save(Brand brand);
//	boolean update(long id, Brand brand);
	
	boolean saveOrUpdate(Brand brand);
	//boolean saveOrUpdate(BrandDTO brand);
	
	boolean delete(long id);
	
	
}
