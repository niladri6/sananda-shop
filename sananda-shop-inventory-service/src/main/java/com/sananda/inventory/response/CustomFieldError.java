package com.sananda.inventory.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sananda.inventory.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CustomFieldError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utils.DATE_TIME_PATTERN) // "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private Map<String, String> error = new HashMap<String, String>();

}
