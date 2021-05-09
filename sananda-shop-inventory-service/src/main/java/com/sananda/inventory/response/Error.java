package com.sananda.inventory.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sananda.inventory.utils.Utils;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Error implements Serializable {
	private static final long serialVersionUID = 1L;
	//private boolean success;
	private String message;
	//private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utils.DATE_TIME_PATTERN) //"dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
//	public Error(Throwable ex) {
//		super();
//		this.description = ex.getLocalizedMessage();
//	}
	
	
}