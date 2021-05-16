package com.sananda.inventory.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.CustomFieldError;
import com.sananda.inventory.response.Error;
import com.sananda.inventory.response.FieldErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	 * RequestBody Validation Exception Handling
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("Validation Failed: " + ex.getMessage());
		final Map<String, String> errors = new HashMap<String, String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}

		FieldErrorResponse errorResponse = new FieldErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);

		CustomFieldError error = new CustomFieldError();
		error.setMessage("Validation Failed");
		error.setTimestamp(LocalDateTime.now());
		error.setError(errors);
		errorResponse.setError(error);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleBaseException(DataIntegrityViolationException ex) {
		System.out.println("0. DataIntegrityViolationException: " + ex.getMessage());
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		// error.setDescription(errorDesc);
		error.setTimestamp(LocalDateTime.now());
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

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

//	@ExceptionHandler(value= {RuntimeException.class})
//	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
//		System.out.println("0. Exception: " + ex.getMessage());
//		String errorDesc = ex.getLocalizedMessage();
//		System.out.println("1. RuntimeException: " + errorDesc);
//		
//		if(errorDesc == null) {
//			errorDesc = ex.toString();
//		}
//		
//		System.out.println("2. RuntimeException: " + errorDesc);
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
//		LocalDateTime timestamp = LocalDateTime.parse(LocalDateTime.now().format(format), format);
//		
////		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
////		String dateString = LocalDateTime.now().format(format);
////		LocalDateTime timestamp = LocalDateTime.parse(dateString, format);
//	    
//		ApiErrorResponse errorResponse = new ApiErrorResponse();
//		errorResponse.setSuccess(Boolean.FALSE);
//		Error error = new Error();
//		error.setMessage(errorDesc);
//		//error.setDescription(errorDesc);
//		error.setTimestamp(timestamp);
//		errorResponse.setError(error);
//		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		String errorDesc = ex.getLocalizedMessage();
		System.out.println("1. ResourceNotFoundException: " + errorDesc);
		if (errorDesc == null) {
			errorDesc = ex.toString();
		}
		System.out.println("2. ResourceNotFoundException: " + errorDesc);

		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		// error.setDescription(errorDesc);
		error.setTimestamp(LocalDateTime.now());
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InvalidDataException.class })
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {

		String errorDesc = ex.getLocalizedMessage();
		if (errorDesc == null) {
			errorDesc = ex.toString();
		}
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		String dateString = LocalDateTime.now().format(format);
//		LocalDateTime timestamp = LocalDateTime.parse(dateString, format);

		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setSuccess(Boolean.FALSE);
		Error error = new Error();
		error.setMessage(ex.getMessage());
		// error.setDescription(errorDesc);
		error.setTimestamp(LocalDateTime.now());
		errorResponse.setError(error);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
