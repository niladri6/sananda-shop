package com.sananda.barcode.controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.barcode.service.BarcodeService;
import com.sananda.barcode.utils.Utils;

@RestController
@RequestMapping("/api")
public class BarcodeController {
	
	@Autowired
	private BarcodeService barcodeService;
	
	@GetMapping("/barcode/{barcode}")
	ResponseEntity<?> getBarcode(@PathVariable String barcode) throws Exception {
		System.out.println("barcodeText: " + barcode);
		
		
		Map<String, Object> map = barcodeService.getBarcodeImage(barcode);
		String html = "<div>\r\n"
				+ " <p>Your Barcode</p>\r\n"
				+ " <img src=\"data:image/png;base64," + map.get("image") +"\" alt=\"Barcode\" />\r\n"
				+ "	<h3 style=\"position: absolute;left: 90px;top: 185px;\">" +map.get("data") + "</h3>"
				+ "</div>";
		return ResponseEntity.status(HttpStatus.OK).body(html);
		
	}
	
	@GetMapping("/barcode/buffer/{barcode}")
	ResponseEntity<?> getBarcodeBufferImage(@PathVariable String barcode) throws Exception {
		System.out.println("barcodeText: " + barcode);
		final BufferedImage buffer = Utils.generateEAN13BarcodeBufferedImage(barcode);
		System.out.println(buffer);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(buffer);
		
	}
	
	

}
