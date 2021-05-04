package com.sananda.inventory.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ApiResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Value("true")
	//@Value("#{new Boolean('true')}")
	private boolean success;
	private List<? extends Object> data;
	

}
