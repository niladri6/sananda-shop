package com.sananda.inventory.response;

import java.io.Serializable;
import java.util.Comparator;

import com.sananda.inventory.entity.Brand;

import lombok.Data;

@Data
public class MenuItems implements Serializable {
	private Long id;
	private String name;

	
}
