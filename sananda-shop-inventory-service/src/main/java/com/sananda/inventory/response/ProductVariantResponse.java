package com.sananda.inventory.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductVariantResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String productName;
	private String productCode;
	private String sku;
	private LocalDate dateOfImport; // distributor
	private LocalDate dateOfPurchase; // shop
	private BigInteger totalQuantity;
	private BigDecimal purchasePrice;
	private String colour;
	private BigDecimal discount;

}
