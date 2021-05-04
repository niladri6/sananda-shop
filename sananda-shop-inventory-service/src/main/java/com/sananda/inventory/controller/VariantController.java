package com.sananda.inventory.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.inventory.common.Constants;
import com.sananda.inventory.dto.ProductDTO;
import com.sananda.inventory.dto.VariantDTO;
import com.sananda.inventory.entity.Product;
import com.sananda.inventory.entity.Variants;
import com.sananda.inventory.exception.InvalidDataException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.ApiResponse;
import com.sananda.inventory.response.ApiSuccessResponse;
import com.sananda.inventory.response.Error;
import com.sananda.inventory.service.ProductService;
import com.sananda.inventory.service.VariantService;
import com.sananda.inventory.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class VariantController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductService productService;

	@Autowired
	private VariantService variantService;

	@GetMapping("/variant/{id}")
	public ResponseEntity<?> getById(@PathVariable(value = "id") long id) throws IOException {
		ApiResponse response = new ApiResponse();

		Variants variant = null;
		try {
			variant = variantService.getById(id);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(variant));
		} catch (ResourceNotFoundException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			// log.info(errorResponse.toString());
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}

		// log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}

	
	// wrong// need mod
	/*
	 * Save variant with different different variants.
	 */
	@PostMapping("product/{productId}/variant")
	public ResponseEntity<?> saveVariant(@PathVariable("productId") Long productId,
			@Valid @RequestBody VariantDTO variantDTO) {
		System.out.println("variant req: " + variantDTO);

		ApiResponse response = new ApiResponse();

		Variants savedVariant = null;
		try {
			Product product = productService.getById(productId);
			savedVariant = variantService.save(variantDTO);
			product.getVariants().add(savedVariant);

			System.out.println("saved variant: " + savedVariant);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(savedVariant));
		} catch (ResourceNotFoundException | InvalidDataException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			// log.info(errorResponse.toString());
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}

	// wrong// need mod
	@PutMapping("product/{productId}/variant/{variantId}")
	public ResponseEntity<?> updateVariantById(@PathVariable(value = "productId") long pId, 
			@PathVariable(value = "variantId") long vId,
			@Valid @RequestBody VariantDTO variantDTO) {

		System.out.println("variant req: " + variantDTO);

		ApiResponse response = new ApiResponse();
		
		Variants updatedVariant = null;
		try {
			Product product = productService.getById(pId);
//			Set<Variants> variants = product.getVariants();
			
			updatedVariant = variantService.update(vId, variantDTO);

			// log.info("updated variant : ", updatedProduct.toString());
			System.out.println("updated variant : " + updatedVariant);

			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(updatedVariant));

		} catch (ResourceNotFoundException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse();
			errorResponse.setSuccess(Boolean.FALSE);
			Error error = new Error();
			error.setMessage(e.getMessage());
			errorResponse.setError(error);
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/variant/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) throws IOException {
		ApiSuccessResponse response = new ApiSuccessResponse();

		try {
			boolean status = variantService.delete(id);
			System.out.println("status: " + status);
			if (status) {
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
			return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
		}

		System.out.println("success resp: " + response);
		// log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}

}
