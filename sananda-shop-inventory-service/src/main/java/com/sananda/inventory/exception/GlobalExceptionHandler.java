package com.sananda.inventory.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.Error;

public class AppExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		
		String errorDesc = ex.getLocalizedMessage();
		if(errorDesc == null) {
			errorDesc = ex.toString();
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		LocalDateTime timestamp = LocalDateTime.parse(LocalDateTime.now().format(format));  
	    
//	    String formatDate = current.format(format);
//	    LocalDateTime dateTime = LocalDateTime.parse(formatDate, format);
	    
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		error.setDescription(errorDesc);
		error.setTimestamp(timestamp);
		errorResponse.setError(error);
	}
	

}
