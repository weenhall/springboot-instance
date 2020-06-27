package com.ween.springbooteasyexcel.demo;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.ween.springbooteasyexcel.indexorname.IndexOrNameData;
import com.ween.springbooteasyexcel.indexorname.IndexOrNameDataListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuickStart {
    private static final String FILE_NAME ="D:/demo.xlsx";

    //读Excel
    @Test
     public void simpleRead(){
        //写法1
        EasyExcel.read(FILE_NAME,DemoData.class,new DemoDataListener()).sheet().doRead();
        //写法2
        ExcelReader reader=EasyExcel.read(FILE_NAME,DemoData.class,new DemoDataListener()).build();
        ReadSheet sheet=EasyExcel.readSheet(1).build();
        reader.read(sheet);
        reader.finish();
    }

    //写Excel
    @Test
    public void simpleWrite(){
         EasyExcel.write(FILE_NAME,DemoData.class).sheet("模板").doWrite(data());
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

    //根据列的索引或列名称读取
    @Test
    public void readByIndexOrName(){
        EasyExcel.read(FILE_NAME, IndexOrNameData.class,new IndexOrNameDataListener()).sheet().doRead();
    }

    //读取多个sheet
    @Test
    public void readMultiSheet(){
        //读取所有
        EasyExcel.read(FILE_NAME,DemoData.class,new DemoDataListener()).doReadAll();
        //读取部分
        ExcelReader reader=EasyExcel.read(FILE_NAME).build();
        ReadSheet sheet1=EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        ReadSheet sheet2=EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        reader.read(sheet1,sheet2);
        reader.finish();
    }

    //同步返回
    @Test
    public void synchronousRead(){
        List<DemoData> list=EasyExcel.read(FILE_NAME).head(DemoData.class).sheet().doReadSync();
        //todo
    }
}
