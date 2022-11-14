package com.example.springbootactiviti.task;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@Slf4j
@SpringBootTest
public class GroupTaskTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	@Test
	public void taskStart(){
		String key="grouptask";
		String tenantId="TEST";
		ProcessInstance instance=coreBase.getRuntimeService()
				.startProcessInstanceByKeyAndTenantId(key,tenantId);
		log.info("租户:{} ,启动流程:{}",instance.getTenantId(),instance.getProcessDefinitionName());
	}

	@Test
	public void queryHRGroupTask(){
		String currentUser="Bob";
		List<Task> taskList=coreBase.getTaskService().createTaskQuery()
				.taskTenantId("TEST")
				.taskCandidateUser(currentUser)
				.list();
		taskList.forEach(item->{
			coreBase.getTaskService().claim(item.getId(),currentUser);
			log.info("用户:{} 已签收:{}",currentUser,item.getName());
			coreBase.getTaskService().complete(item.getId());
			log.info("用户:{} 已完成:{}",currentUser,item.getName());
		});
	}

	@Test
	public void queryMANAGERGroupTask(){
		String currentUser="Pat";
		List<Task> taskList=coreBase.getTaskService().createTaskQuery()
				.taskTenantId("TEST")
				.taskCandidateUser(currentUser)
				.list();
		taskList.forEach(item->{
			coreBase.getTaskService().claim(item.getId(),currentUser);
			log.info("用户:{} 已签收:{}",currentUser,item.getName());
			coreBase.getTaskService().complete(item.getId());
			log.info("用户:{} 已完成:{}",currentUser,item.getName());
		});
	}
}
