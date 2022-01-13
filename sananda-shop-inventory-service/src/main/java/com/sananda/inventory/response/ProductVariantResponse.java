package com.sananda.inventory.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ProductVariantResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private String name; // productName
//	private String code; // productCode
//	private String sku;
//	private LocalDate dateOfImport; // distributor
//	private LocalDate dateOfPurchase; // shop
//	private BigInteger totalQuantity;
//	private BigDecimal purchasePrice;
//	private String colour;
//	private BigDecimal discount;

	private String name; // productName
	private LocalDate distImportDate;
	private LocalDate shopImportDate;
	
	// variats
	private String sku;
	private BigDecimal mrp;
	private BigDecimal discount;
	private String size;
	private String colour;
	private BigInteger totalQuantity;
}
