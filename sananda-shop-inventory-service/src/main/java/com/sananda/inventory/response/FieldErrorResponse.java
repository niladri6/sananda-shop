package com.sananda.inventory.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FieldErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean success;
	private CustomFieldError error;
	
//	@Data
//	static class Error {
//		private String message;
//		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utils.DATE_TIME_PATTERN) //"dd-MM-yyyy hh:mm:ss")
//		private LocalDateTime timestamp;
//		private Map<String, String> error = new HashMap<String, String>();
//		
//	}

}
