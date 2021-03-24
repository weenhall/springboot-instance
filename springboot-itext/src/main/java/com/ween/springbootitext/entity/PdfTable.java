package com.ween.springbootitext.entity;

import java.util.List;
import java.util.Map;

public class PdfTable {

    private String fileName;
    private String[] cellHeader;
    private List<Map<String,Object>> cellBody;

    public String[] getCellHeader() {
        return cellHeader;
    }

    public void setCellHeader(String[] cellHeader) {
        this.cellHeader = cellHeader;
    }

    public List<Map<String, Object>> getCellBody() {
        return cellBody;
    }

    public void setCellBody(List<Map<String, Object>> cellBody) {
        this.cellBody = cellBody;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
