package com.sananda.barcode.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sananda.barcode.utils.Utils;

@Service
public class BarcodeService {
	final static String PDF_PATH = "src\\main\\resources\\static\\pdfs";
	
	public Map<String, Object> getBarcodeImage(String barcodeText) throws Exception {
		return Utils.generateEAN13BarcodeImage(barcodeText);
	}
	
	
//	public Map<String, Object> getBarcodePdf(String path) throws Exception {
//		return Utils.generateEAN13BarcodeImage(barcodeText);
//	}
	

}
