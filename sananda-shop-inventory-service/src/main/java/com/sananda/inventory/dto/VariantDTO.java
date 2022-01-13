package com.sananda.inventory.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VariantDTO {
	private Long id;
	private String sku;
	
	@NotNull(message = "mrp is required")
	private BigDecimal mrp;
	
	private BigDecimal discount;
	
	private String size;
	
	@NotBlank(message = "colour may not be null")
	private String colour;
	
	@NotNull(message = "quantity may not be null")
	private BigInteger quantity;
}
