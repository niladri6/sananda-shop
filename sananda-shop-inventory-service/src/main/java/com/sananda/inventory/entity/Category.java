package com.sananda.inventory.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
@EqualsAndHashCode(callSuper=false, exclude="brands")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"brands", "products"})
@Entity
@Table(name = "category")
public class Category extends Audit {
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
	
	@Column(name="description")
	private String description;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "brand_id")
//	@JsonBackReference
//	private Brand brand;
	
	//@JsonIgnore
	//@JsonBackReference
	
	@JsonBackReference
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Brand> brands; // = new HashSet<>();
	
	//@JsonIgnore
	@OneToMany(mappedBy = "category", targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Product> products;
}
