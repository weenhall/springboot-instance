package com.ween.springbootitext.utils;

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
import com.ween.springbootitext.entity.PdfTable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfUtils {

    public static ByteArrayOutputStream generate(PdfTable pdfTable) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream(4096);
        PdfDocument pdfDocument=new PdfDocument(new PdfWriter(baos));
        Document document=new Document(pdfDocument, PageSize.A4.rotate());

        Table table=new Table(UnitValue.createPercentArray(pdfTable.getCellHeader().length)).useAllAvailableWidth();
        PdfFont pdfFont= PdfFontFactory.createFont("STSongStd-Light","UniGB-UCS2-H",false);
        //set cellHeader
        for (int i = 0; i < pdfTable.getCellHeader().length; i++) {
            Cell header=new Cell();
            header.setFont(pdfFont).setBackgroundColor(new DeviceRgb(64,64,64));
            header.setPadding(5);
            header.add(new Paragraph(pdfTable.getCellHeader()[i]));
            header.setFontColor(DeviceRgb.WHITE);
            table.addHeaderCell(header);
        }
        pdfTable.getCellBody().forEach(map->{
            map.forEach((k,v)->{
                table.addCell(new Cell().add(new Paragraph(String.valueOf(v)).setFont(pdfFont)));
            });
        });
        document.add(table);
        document.close();
        return baos;
    }
}
