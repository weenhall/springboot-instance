package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Slf4j
@SpringBootTest
public class ProcessDefinitionTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	/**
	 * 获取所有流程定义
	 */
	@Test
	public void listOfProcessDefinitions(){
		ProcessDefinitionQuery query = coreBase.getRepositoryService().createProcessDefinitionQuery();
		query.latestVersion();
		long count=query.count();
		log.info("流程总数:{}",count);
		List<ProcessDefinition> list=query.listPage(0,10);
		list.forEach(item->{
			log.info("====================");
			log.info("Id:{}",item.getId());
			log.info("版本:{}",item.getVersion());
			log.info("key:{}",item.getKey());
			log.info("类别:{}",item.getCategory());
			log.info("是否挂起:{}",item.isSuspended()?"是":"否");
			log.info("名称:{}",item.getName());
			log.info("流程描述:{}",item.getDescription());
			//输出流程图片
			ProcessDiagramGenerator generator=coreBase.getProcessEngine().getProcessEngineConfiguration().getProcessDiagramGenerator();
			BpmnModel model=coreBase.getRepositoryService().getBpmnModel(item.getId());
			InputStream in=generator.generateDiagram(model,"png","宋体", "宋体", "宋体",null,2.0);
			try {
				FileUtils.copyInputStreamToFile(in,new File(item.getDiagramResourceName()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * 获取流程定义中用户节点
	 */
	@Test
	public void listUserTaskElement(){
		String processDefinitionId="leaveapply:1:135004";
		BpmnModel model=coreBase.getRepositoryService().getBpmnModel(processDefinitionId);
		if(model!=null){
			Collection<FlowElement> flowElements=model.getMainProcess().getFlowElements();
			for(FlowElement element:flowElements){
				if(element instanceof StartEvent){
					log.info("======================");
					log.info("Task Id:{}",element.getId());
					log.info("Task Name:{}",element.getName());
				} else if(element instanceof UserTask){
					log.info("======================");
					log.info("Task Id:{}",element.getId());
					log.info("Task Name:{}",element.getName());
				}
			}
		}
	}
}
