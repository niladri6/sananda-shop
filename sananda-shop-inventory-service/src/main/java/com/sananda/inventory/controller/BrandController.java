package com.sananda.inventory.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.sananda.inventory.dto.BrandDTO;
import com.sananda.inventory.entity.Brand;
import com.sananda.inventory.exception.CustomException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.ApiResponse;
import com.sananda.inventory.response.ApiSuccessResponse;
import com.sananda.inventory.response.Error;
import com.sananda.inventory.service.BrandService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping("/brand")
	public ResponseEntity<?> getBrands() {
		ApiResponse response = new ApiResponse();
		List<Brand> brands = brandService.getBrands();
		response.setSuccess(Boolean.TRUE);
		response.setData(brands);
		//log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/brand/{id}")
	public ResponseEntity<?> getBrandById(@PathVariable(value="id") long id) throws IOException {
		ApiResponse response = new ApiResponse();
		
		Brand brands = null;
		try {
			//Brand brands = brandService.getById(id);
			brands = brandService.getById(id);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(brands));
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
	
	@PostMapping("/brand")
	public ResponseEntity<?> saveBrand(@Valid @RequestBody BrandDTO brand) {
		//log.info("brand req: ", brand.toString());
		
		System.out.println("brand req: "+ brand);
		
		
		ApiResponse response = new ApiResponse();
		//ApiSuccessResponse response = new ApiSuccessResponse();
		
		Brand savedBrand = null;
		try {
			savedBrand = brandService.save(brand);
			System.out.println("saved brand : " + savedBrand);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(savedBrand));
				//response.setStatus(ResponseStatus.SUCCESS);
				//response.setSuccess(Boolean.TRUE);
				//response.setMessage(Constants.SAVED_MSG);
			
//			response.setSuccess(Boolean.TRUE);
//			response.setData(Arrays.asList(brands));
		} catch (CustomException e) {
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
	
	@PutMapping("/brand/{id}")
	public ResponseEntity<?> updateBrandById(@PathVariable(value="id") long id, @Valid @RequestBody BrandDTO brand) {
		//log.info("brand req: ", brand.toString());
		
		System.out.println("brand req: "+ brand);
		
		
		ApiResponse response = new ApiResponse();
		//ApiSuccessResponse response = new ApiSuccessResponse();
		
		Brand updatedBrand = null;
		try {
			//Brand brands = brandService.getById(id);
			updatedBrand = brandService.update(id, brand);
			
			//log.info("updated brand : ", updatedBrand.toString());
			System.out.println("updated brand : " + updatedBrand);
			//log.info("equals ?", brand.equals(updatedBrand));
			//System.out.println("equals ? : " + brand.getId().equals(updatedBrand.getId()));
			
			
			//if(updatedBrand != null) {
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(updatedBrand));
				
			//}
				//response.setStatus(ResponseStatus.SUCCESS);
				//response.setSuccess(Boolean.TRUE);
				//response.setMessage(Constants.UPDATE_MSG);
			
//			response.setSuccess(Boolean.TRUE);
//			response.setData(Arrays.asList(brands));
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
	
	@DeleteMapping("/brand/{id}")
	public ResponseEntity<?> deleteBrandById(@PathVariable(value="id") long id) throws IOException {
		ApiSuccessResponse response = new ApiSuccessResponse();
		
		try {
			boolean status = brandService.delete(id);
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
