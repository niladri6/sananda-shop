package com.sananda.inventory.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sananda.inventory.common.Audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "product")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "variants")
public class Variants extends Audit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	// @Column(name="code", unique = true)
	@Column(name = "code")
	private String code;

	@Column(name = "mrp")
	private BigDecimal mrp;

	@Column(name = "discount")
	private BigDecimal discount;

	@Column(name = "size")
	private String size;

	@Column(name = "colour")
	private String colour;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "product_id")
//	@JsonBackReference
//	private Product product;

//	@JsonBackReference
//	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
//			CascadeType.MERGE, CascadeType.REFRESH })
//	private Set<Product> products;

	// UNI-DIRECTIONAL MAPPING
//	@JsonBackReference
//	@ManyToOne(fetch = FetchType.LAZY) // , optional = false
//	@JoinColumn(name = "product_id")
//	private Product product;

//	@OneToMany(mappedBy = "variant", targetEntity = ProductSize.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<ProductSize> size;
//	
//	@OneToMany(mappedBy = "variant", targetEntity = ProductColour.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<ProductColour> colour;

}
