package com.ween.springbootitext.controller;

import com.ween.springbootitext.entity.PdfTable;
import com.ween.springbootitext.utils.PdfUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/itext")
public class ItextController {

    private final List<Map<String,Object>> cellBody=new ArrayList<>();
    private final PdfTable pdfTable=new PdfTable();

    @GetMapping("/export")
    public void exportPDF(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos= PdfUtils.generate(pdfTable);
        response.setContentType("text/plain;charaset=utf-8");
        response.setContentType("application/vnd.ms-pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+pdfTable.getFileName());
        response.setContentLength(baos.size());
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }

    @PostConstruct
    public void generateData(){
        for (int i = 0; i <500 ; i++) {
            Map<String,Object> map=new LinkedHashMap<>();
            map.put("xm","张三"+i);
            map.put("xb","李四"+i);
            map.put("age",18);
            cellBody.add(map);
        }
        pdfTable.setCellHeader(new String[]{"姓名","性别","年龄"});
        pdfTable.setCellBody(cellBody);
        pdfTable.setFileName("demo.pdf");
    }
}
