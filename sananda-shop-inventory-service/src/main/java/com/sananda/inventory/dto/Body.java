package com.sananda.inventory.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
public class Body implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NonNull
	Map<String, Long> param = new HashMap();
	

}
