package com.sananda.barcode.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtryPoint {
	
	@GetMapping("/")
	ResponseEntity<?> entry() {
		System.out.println("Hi");
//		System.out.println(barcodeService.getBarcodeImage(barcodeText));
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", "1");
		map.put("name", "barcode-service");
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
