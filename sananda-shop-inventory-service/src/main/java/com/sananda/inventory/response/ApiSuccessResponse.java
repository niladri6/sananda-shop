package com.sananda.inventory.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ApiSuccessResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ResponseStatus status;
	private boolean success;
	private String message;
	

}
