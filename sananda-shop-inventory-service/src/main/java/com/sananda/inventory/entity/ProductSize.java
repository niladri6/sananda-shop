package com.sananda.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

//@Entity
public class ProductSize {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@Column
//	private String size;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "variant_id")
//	@JsonBackReference
//	private Variants variant;
	
//	@JsonBackReference
//	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.DETACH,
//			CascadeType.MERGE, CascadeType.REFRESH })
//	private Set<Product> product;

}
