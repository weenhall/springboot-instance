package com.ween.springbooteasyexcel.indexorname;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IndexOrNameDataListener extends AnalysisEventListener<IndexOrNameData>{
    private static final Logger logger = LoggerFactory.getLogger(IndexOrNameDataListener.class);
    private static final int BATCH_COUNT=5;
    List<IndexOrNameData> listOfData=new ArrayList<>();

    @Override
    public void invoke(IndexOrNameData data, AnalysisContext analysisContext) {
        logger.info("解析到一条数据:{}", JSON.toJSONString(data));
        listOfData.add(data);
        if (listOfData.size() >= BATCH_COUNT) {
            dataManual();
            listOfData.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        dataManual();
        logger.info("所有数据解析完成");
    }

    private void dataManual(){
        logger.info("{}条数据，开始存储数据库！", listOfData.size());
        logger.info("存储数据库成功！");
    }
}
