package com.sananda.inventory.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.ProductDTO;
import com.sananda.inventory.entity.Product;
import com.sananda.inventory.exception.CustomException;
import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.ApiResponse;
import com.sananda.inventory.response.ApiSuccessResponse;
import com.sananda.inventory.response.Error;
import com.sananda.inventory.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/*
	 * Get All products...
	 * */
	@GetMapping("/product")
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		
		Page<Product> products = productService.findAll(paging);
		
		
		ApiResponse response = new ApiResponse();
		response.setSuccess(Boolean.TRUE);
		response.setData(products.getContent());
		//log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}
	
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable(value="id") long id) throws IOException {
		ApiResponse response = new ApiResponse();
		
		Product product = null;
		try {
			product = productService.getById(id);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(product));
		} catch (ResourceNotFoundException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			//log.info(errorResponse.toString());
			return new ResponseEntity<ApiErrorResponse>(errorResponse ,HttpStatus.EXPECTATION_FAILED);
		}	
		
		//log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}
	
	/*
	 * Save product with different different variants.
	 * */
	@PostMapping("/product")
	public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
		//log.info("product req: ", product.toString());
		
		System.out.println("product req: "+ productDTO);
		
		ApiResponse response = new ApiResponse();
		
		Product savedProduct = null;
		try {
			savedProduct = productService.save(productDTO);
			System.out.println("saved product: " + savedProduct);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(savedProduct));
		} catch (ResourceNotFoundException | InvalidDataException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			//log.info(errorResponse.toString());
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}	
		
		//log.info(response.toString());
		//return ResponseEntity.ok().body(response);
		//return ResponseEntity.accepted().body(response);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProductById(@PathVariable(value="id") long id, @Valid @RequestBody ProductDTO productDTO) {
		//log.info("product req: ", product.toString());
		
		System.out.println("product req: "+ productDTO);
		
		
		ApiResponse response = new ApiResponse();
		//ApiSuccessResponse response = new ApiSuccessResponse();
		
		Product updatedProduct = null;
		try {
			updatedProduct = productService.update(id, productDTO);
			
			//log.info("updated product : ", updatedProduct.toString());
			System.out.println("updated product : " + updatedProduct);
			
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(updatedProduct));
			
		} catch (ResourceNotFoundException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			//log.info(errorResponse.toString());
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}	
		
		//log.info(response.toString());
		//return ResponseEntity.ok().body(response);
		//return ResponseEntity.accepted().body(response);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}
	
	
	
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable(value="id") long id) throws IOException {
		ApiSuccessResponse response = new ApiSuccessResponse();
		
		try {
			boolean status = productService.delete(id);
			System.out.println("status: "+ status);
			if(status) {
				response.setSuccess(Boolean.TRUE);
				response.setMessage(Constants.DELETED_MSG);
			} else {
				response.setSuccess(Boolean.FALSE);
				response.setMessage(Constants.NOT_DELETED_MSG);
			}
		} catch (ResourceNotFoundException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			//log.info(errorResponse.toString());
			System.out.println("error resp: "+ errorResponse);
			return new ResponseEntity<ApiErrorResponse>(errorResponse ,HttpStatus.EXPECTATION_FAILED);
		}
		
		System.out.println("success resp: "+ response);
		//log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}
	
	

}
