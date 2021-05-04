package com.sananda.inventory.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.Data;

@Data
public class VariantDTO {
	private Long id;
	private String code;
	private BigDecimal mrp;
	private BigDecimal discount;
	private String size;
	private String colour;
}
