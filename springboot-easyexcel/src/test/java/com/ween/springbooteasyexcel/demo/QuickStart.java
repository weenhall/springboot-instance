package com.ween.springbooteasyexcel.demo;


import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuickStart {

    //读Excel
    @Test
     public void simpleRead(){
        String fileName="D:/demo.xlsx";
        EasyExcel.read(fileName,DemoData.class,new DemoDataListener()).sheet().doRead();
    }

    //写Excel
    @Test
    public void simpleWrite(){
         String  fileName="D:/demo.xlsx";
         EasyExcel.write(fileName,DemoData.class).sheet("模板").doWrite(data());
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setUser("user"+i);
            data.setPwd("123456");
            list.add(data);
        }
        return list;
    }
}
