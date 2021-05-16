package com.sananda.inventory.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.inventory.common.Constants;
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

//	@Autowired
//	private VariantRepository variantRepo;

	/*
	 * GET PRODUCT VARIANTS
	 */
	@PostMapping("/variants")
	public ResponseEntity<?> getProductVariants(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
			@RequestBody Map<String, Long> param) {
		// Pageable paging = PageRequest.of(page, size,
		// Sort.by("createdAt").descending());

		// ApiResponse response = new ApiResponse();
		// Product product = productService.getById(productId);

		for (Map.Entry<String, Long> entry : param.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}

		// working code
//		if (!param.containsKey("product_id") || (param.get("product_id") == null )) {
//			throw new InvalidDataException("product_id is required");
//		}

		// experiment
		if (Utils.isKeyValueNull(param, "product_id")) {
			throw new InvalidDataException("product_id is required");
		}

		Long productId = param.get("product_id");
		// if(productId == null ) throw new InvalidDataException("product_id is
		// required");
		System.out.println("productId: " + productId);

		List<Variants> variants = variantService.getByProductId(productId);

		// System.out.println("variants: " + variants.toString());

		ApiResponse response = new ApiResponse();
		response.setSuccess(Boolean.TRUE);
		response.setData(variants);
		return ResponseEntity.ok().body(response);
	}

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

	/*
	 * Save variant in a product.
	 */
	@PostMapping("product/{productId}/variant")
	public ResponseEntity<?> saveVariant(@PathVariable("productId") Long productId,
			@Valid @RequestBody VariantDTO variantDTO) {
		System.out.println("variant req: " + variantDTO);

		ApiResponse response = new ApiResponse();
		Product product = productService.getById(productId);

		Variants variant = variantService.save(productId, variantDTO);
		if (!Utils.isEmpty(variant)) {
			product.addVariant(variant); // for association
		}

		System.out.println("saved variant: " + variant);
		response.setSuccess(Boolean.TRUE);
		response.setData(Arrays.asList(product.getVariants()));

		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}

	@PutMapping("product/{productId}/variant/{variantId}")
	public ResponseEntity<?> updateVariantById(@PathVariable(value = "productId") long pId,
			@PathVariable(value = "variantId") long vId, @Valid @RequestBody VariantDTO variantDTO) {

		System.out.println("variant req: " + variantDTO);
		
		ApiResponse response = new ApiResponse();

		Variants updatedVariant = null;
		if (productService.existsById(pId)) {
			updatedVariant = variantService.update(vId, variantDTO);
		}

		System.out.println("updated variant : " + updatedVariant);

		if (Utils.isEmpty(updatedVariant)) {
			response.setSuccess(Boolean.FALSE);
		} else {
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(updatedVariant));
		}

		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/variant/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) throws IOException {
		ApiSuccessResponse response = new ApiSuccessResponse();
		boolean status = variantService.delete(id);
		System.out.println("status: " + status);
		if (status) {
			response.setSuccess(Boolean.TRUE);
			response.setMessage(Constants.DELETED_MSG);
		} else {
			response.setSuccess(Boolean.FALSE);
			response.setMessage(Constants.NOT_DELETED_MSG);
		}
		System.out.println("success resp: " + response);
		return ResponseEntity.ok().body(response);
	}

}
