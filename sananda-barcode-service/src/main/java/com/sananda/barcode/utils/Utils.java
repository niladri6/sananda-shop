package com.sananda.barcode.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Utils {

	public final static String BARCODE_PATH = "src\\main\\resources\\static\\barcodes";
	public final static String PDF_PATH = "src\\main\\resources\\static\\pdfs\\test.pdf";

	public static Map<String, Object> generateEAN13BarcodeImage(String barcodeText) throws Exception {
		EAN13Writer barcodeWriter = new EAN13Writer();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 300, 150);

		final String imageName = barcodeText.concat(".png");
		System.out.println("image name: " + imageName);

//		File file = new File(FILE_PATH + File.pathSeparator + imageName);
//		boolean result = file.createNewFile();
//		System.out.println("file created? " + result);

		String path = createBarcodeFile(barcodeText, bitMatrix);
//		if(result) {
//			MatrixToImageWriter.writeToPath(bitMatrix, "png", Paths.get(file.getPath()));
//			System.out.println("Barcode Created.");
//		}
		Map<String, Object> map = new HashMap<>();
		if (path != null) {
			String encodedImg = encodeImageToBase64(path);
			System.out.println("base64: " + encodedImg);
			map.put("data", barcodeText.trim());
			map.put("image", encodedImg);
		}

		System.out.println("Object: " + map);

		return map;
	}

	// Convert Image File to Base64 String
	public static String encodeImageToBase64(String filePath) throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		System.out.println("Barcode encoded to Base64.");
		return encodedString;
	}

	public static String decodeBase64ToImage(String encodedString) throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String outputFileName = "src\\main\\resources\\static\\barcode\\decodedImage.png";
		FileUtils.writeByteArrayToFile(new File(outputFileName), decodedBytes);
		return encodedString;
	}

	public static BufferedImage generateEAN13BarcodeBufferedImage(String barcodeText) throws Exception {
		EAN13Writer barcodeWriter = new EAN13Writer();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 300, 150);
		BufferedImage bufferImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		return bufferImage;
	}

	public static String createBarcodeFile(String fileName, BitMatrix bitMatrix) throws IOException {
		final String imageName = fileName.concat("png");
		File file = new File(BARCODE_PATH + File.pathSeparator + imageName);
		boolean result = file.createNewFile();

		if (result) {
			MatrixToImageWriter.writeToPath(bitMatrix, "png", Paths.get(file.getAbsolutePath()));
			System.out.println("Barcode Created.");
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}

	// itext pdf
//	public static void createPdf(String dest, String code) throws IOException, DocumentException {
//		Document document = new Document();
//		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
//		document.open();
//		PdfPTable table = new PdfPTable(4);
//		table.setWidthPercentage(100);
//		for (int i = 0; i < 14; i++) {
//			System.out.println(code);
//			table.addCell(createBarcode(writer, code));
//		}
//		document.add(table);
//		document.close();
//	}
	
	public static void createPdf(String dest, List<String> codes) throws IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		for (String code: codes) {
			System.out.println(code);
			table.addCell(createBarcode(writer, code));
		}
		table.completeRow();
		document.add(table);
		document.close();
	}

	public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {
		BarcodeEAN barcode = new BarcodeEAN();
		barcode.setCodeType(Barcode.EAN13);
		barcode.setCode(code);
		PdfPCell cell = new PdfPCell(
				barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
		cell.setPadding(10);
		return cell;
	}

}
