package com.ween.springbooteasyexcel.demo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板读取类
 */
public class DemoDataListener extends AnalysisEventListener<DemoData> {
    private static final Logger logger = LoggerFactory.getLogger(DemoDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<DemoData> list = new ArrayList<DemoData>();

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        logger.info("读取到一条数据：{}", JSON.toJSONString(demoData));
        list.add(demoData);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    /**
     * 所有数据解析完成后调用此方法
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("所有数据解析完成");
        saveData();
    }

    private void saveData() {
        logger.info("{} 条数据，开始存到数据库", list.size());
        //todo dao.save()
    }
}
