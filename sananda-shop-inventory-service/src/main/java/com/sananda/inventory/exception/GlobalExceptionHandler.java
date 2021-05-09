package com.sananda.inventory.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.Error;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
//	@ExceptionHandler(value= {Exception.class})
//	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
//		
//		String errorDesc = ex.getLocalizedMessage();
//		if(errorDesc == null) {
//			errorDesc = ex.toString();
//		}
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		String dateString = LocalDateTime.now().format(format);
//		LocalDateTime timestamp = LocalDateTime.parse(dateString, format);  
//	    
//		ApiErrorResponse errorResponse = new ApiErrorResponse();
//		errorResponse.setSuccess(Boolean.FALSE);
//		Error error = new Error();
//		error.setMessage(ex.getMessage());
//		error.setDescription(errorDesc);
//		error.setTimestamp(LocalDateTime.now());
//		errorResponse.setError(error);
//		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	@ExceptionHandler(value= {RuntimeException.class})
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		
		String errorDesc = ex.getLocalizedMessage();
		if(errorDesc == null) {
			errorDesc = ex.toString();
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		LocalDateTime timestamp = LocalDateTime.parse(LocalDateTime.now().format(format), format);
		
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		String dateString = LocalDateTime.now().format(format);
//		LocalDateTime timestamp = LocalDateTime.parse(dateString, format);
	    
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		//error.setDescription(errorDesc);
		error.setTimestamp(timestamp);
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value= {ResourceNotFoundException.class})
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		
		String errorDesc = ex.getLocalizedMessage();
		if(errorDesc == null) {
			errorDesc = ex.toString();
		}
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
//		LocalDateTime timestamp = LocalDateTime.parse(LocalDateTime.now().format(format));  
	    
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		//error.setDescription(errorDesc);
		error.setTimestamp(LocalDateTime.now());
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value= {InvalidDataException.class})
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
		
		String errorDesc = ex.getLocalizedMessage();
		if(errorDesc == null) {
			errorDesc = ex.toString();
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String dateString = LocalDateTime.now().format(format);
		LocalDateTime timestamp = LocalDateTime.parse(dateString, format);
	    
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		//error.setDescription(errorDesc);
		error.setTimestamp(LocalDateTime.now());
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	

}
