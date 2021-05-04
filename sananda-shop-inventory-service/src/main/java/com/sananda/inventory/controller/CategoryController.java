package com.sananda.inventory.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

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
import com.sananda.inventory.dto.CategoryDTO;
import com.sananda.inventory.entity.Category;
import com.sananda.inventory.exception.CustomException;
import com.sananda.inventory.exception.ResourceNotFoundException;
import com.sananda.inventory.response.ApiErrorResponse;
import com.sananda.inventory.response.ApiResponse;
import com.sananda.inventory.response.ApiSuccessResponse;
import com.sananda.inventory.response.Error;
import com.sananda.inventory.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/category")
	public ResponseEntity<?> getCategorys() {
		ApiResponse response = new ApiResponse();
		List<Category> categories = categoryService.getAll();
		response.setSuccess(Boolean.TRUE);
		response.setData(categories);
		//log.info(response.toString());
		return ResponseEntity.ok().body(response);
	}
	
	
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable(value="id") long id) throws IOException {
		ApiResponse response = new ApiResponse();
		
		Category category = null;
		try {
			//Category categories = categoryService.getById(id);
			category = categoryService.getById(id);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(category));
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
	
	@PostMapping("/category")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		//log.info("category req: ", category.toString());
		
		System.out.println("category req: "+ categoryDTO);
		
		
		ApiResponse response = new ApiResponse();
		//ApiSuccessResponse response = new ApiSuccessResponse();
		
		Category savedCategory = null;
		try {
			savedCategory = categoryService.save(categoryDTO);
			System.out.println("saved category: " + savedCategory);
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(savedCategory));
			//response.setStatus(ResponseStatus.SUCCESS);
			//response.setSuccess(Boolean.TRUE);
			//response.setMessage(Constants.SAVED_MSG);
			
			//response.setSuccess(Boolean.TRUE);
			//response.setData(Arrays.asList(categories));
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
	
	@PutMapping("/category/{id}")
	public ResponseEntity<?> updateCategoryById(@PathVariable(value="id") long id, @Valid @RequestBody CategoryDTO categoryDTO) {
		//log.info("category req: ", category.toString());
		
		System.out.println("category req: "+ categoryDTO);
		
		
		ApiResponse response = new ApiResponse();
		//ApiSuccessResponse response = new ApiSuccessResponse();
		
		Category updatedCategory = null;
		try {
			//Category categories = categoryService.getById(id);
			updatedCategory = categoryService.update(id, categoryDTO);
			
			//log.info("updated category : ", updatedCategory.toString());
			System.out.println("updated category : " + updatedCategory);
			
			//if(updatedCategory != null) {
			response.setSuccess(Boolean.TRUE);
			response.setData(Arrays.asList(updatedCategory));
				
			//}
				//response.setStatus(ResponseStatus.SUCCESS);
				//response.setSuccess(Boolean.TRUE);
				//response.setMessage(Constants.UPDATE_MSG);
			
//			response.setSuccess(Boolean.TRUE);
//			response.setData(Arrays.asList(categories));
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
	
	
	
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable(value="id") long id) throws IOException {
		ApiSuccessResponse response = new ApiSuccessResponse();
		
		try {
			boolean status = categoryService.delete(id);
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
