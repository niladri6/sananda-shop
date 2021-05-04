package com.sananda.inventory.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ApiErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
//	private boolean success; // error
//	private List<? extends Object> message;
	//@Value("false")
	private boolean success;
	private Error error;

}
