package com.sananda.inventory.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sananda.inventory.common.Audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false, exclude={"variants", "category"})
@ToString(exclude = {"variants", "category"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends Audit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
//	@Column(name="code")
//	private String code;
//	
//	@Column(name="mrp")
//	private Double mrp;
//	
//	@Column(name="discount")
//	private String discount;
	
	@Column(name="dist_import_date")
	private LocalDate distImportDate;
	
	@Column(name="shop_import_date")
	private LocalDate shopImportDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonBackReference
	private Category category;
	
//	@JsonManagedReference
//	@ManyToMany(targetEntity = Variants.class, fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
//			CascadeType.MERGE, CascadeType.REFRESH })
//	@JoinTable(name = "product_variants", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "variant_id") })
//	private Set<Variants> variants;
	
	// UNI-DIRECTIONAL MAPPING
	
	@JsonManagedReference
	@OrderBy("created_at DESC")
	@OneToMany(targetEntity = Variants.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Set<Variants> variants;
	
	public void addVariant(Variants variant) {
		if(variants == null) {
			variants = new HashSet<Variants>();
		}
		variants.add(variant);
	}
	
	public void removeVariant(Variants variant) {
		if(!variants.isEmpty()) {
		variants.remove(variant);
		}
//		variant.setProduct(null);
//		this.variants.remove(variant);
	}
	
}
