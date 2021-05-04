package com.sananda.inventory.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sananda.inventory.common.Audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false, exclude="categories")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand extends Audit {
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
	
//	@OneToMany(mappedBy = "brand", targetEntity = Category.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private Set<Category> categories;
	
	
	@JsonManagedReference
	@ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
        name = "brand_category", 
        joinColumns = { @JoinColumn(name = "brand_id", referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "category_id", referencedColumnName = "id") }
    )
    Set<Category> categories; // = new HashSet<>();
	
//	@OneToMany(mappedBy = "brand", targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonManagedReference
//	private List<Product> products;
	
	
}
