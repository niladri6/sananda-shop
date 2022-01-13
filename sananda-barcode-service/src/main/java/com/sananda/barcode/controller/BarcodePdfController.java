package com.sananda.barcode.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sananda.barcode.utils.Utils;

@RestController
@RequestMapping("/api")
public class BarcodePdfController {

	@GetMapping("/barcode-pdf/{barcode}")
	ResponseEntity<?> getBarcode(@PathVariable String barcode) throws Exception {
		System.out.println("barcodeText: " + barcode);
		List<String> list = new ArrayList<>();
		list.add("2109081638244");
		list.add("2109081638243");
		list.add("2109081638242");
		list.add("2109081638241");
		list.add("2109081638240");
		list.add("2109081638245");
		list.add("2111241634294");
		list.add("2201062035339");
		list.add("2201062036367");
		list.add("2201062041583");
		list.add("2201071100028");
		list.add("2201062037326");
		list.add("2201071100028");
		list.add("2201071101377");
		list.add("2112142329227");
		list.add("2201061917438");
		list.add("2112142327247");
		list.add("2110231638533");
		list.add("2110231730183");
		list.add("2108101741537");
		list.add("2111242140244");
		list.add("2111242140240");
		list.add("2111242140246");
		
		System.out.println("list length: " + list.size());
		Utils.createPdf(Utils.PDF_PATH, list);

//		InputStream
//		byte[] Icontents = IOUtils.toByteArray();

		File file = new File(Utils.PDF_PATH);
		byte[] contents = Files.readAllBytes(file.toPath());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		String filename = file.getName();
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		return response;
	}

}
