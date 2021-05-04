package com.sananda.inventory.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	private String name;
	private LocalDate distImportDate;
	private LocalDate shopImportDate;
	private int categoryId;
	private Set<VariantDTO> variants;

}
