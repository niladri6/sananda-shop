package com.sananda.inventory.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class CategoryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;

}
