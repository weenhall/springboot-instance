package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessHistoryTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	@Test
	public void processHistoryQuery(){
		List<ProcessInstance> listOfPI=coreBase.getRuntimeService().createProcessInstanceQuery().active().list();
		listOfPI.forEach(pi->{
			log.info("流程实例:{}",pi.getName());
			log.info("流转历史:");
			List<HistoricTaskInstance> list=coreBase.getHistoryService()
					.createHistoricTaskInstanceQuery()
					.processInstanceId(pi.getId())
					.list();
			if(!CollectionUtils.isEmpty(list)){
				for (HistoricTaskInstance historicTaskInstance : list) {
					log.info("名称:{}",historicTaskInstance.getName());
					log.info("开始时间:{}",historicTaskInstance.getStartTime());
					log.info("结束时间:{}",historicTaskInstance.getEndTime());
					log.info("办理人:{}",historicTaskInstance.getAssignee());
//					log.info("处理意见:{}",historicTaskInstance.get);
				}
			}
		});
	}
}
