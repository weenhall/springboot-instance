package com.ween.springbootitext;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//@SpringBootTest
class SpringbootItextApplicationTests {

	public static final String DEST="demo1.pdf";

	@Test
	void exportPdf() throws Exception {

		PdfDocument pdf=new PdfDocument(new PdfWriter(DEST));
		Document doc=new Document(pdf,PageSize.A4.rotate());

		Table table=new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
		PdfFont pdfFont= PdfFontFactory.createFont("STSongStd-Light","UniGB-UCS2-H",true);

		for(int j=0;j<8;j++){
			Cell header=new Cell();
			header.setFont(pdfFont).setBackgroundColor(new DeviceRgb(64,64,64));
			header.setPadding(5);
			header.add(new Paragraph("标题"));
			header.setFontColor(DeviceRgb.WHITE);
			table.addHeaderCell(header);
		}
		for (int i = 0; i < 16; i++) {
			table.addCell(new Cell().add(new Paragraph("你好")).setFont(pdfFont));
		}
		doc.add(table);
		doc.close();
	}

}
