package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicTaskAssignTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	private final static String PROCESS_KEY="leaveapply";
	private final static String processInstanceId="137501";
	private final static String TENANT_ID="TEST";
	private final static String USER_STARTER="Bob";
	private final static String USER_HANDLER="Alice";

	@Test
	public void startProcess(){
		coreBase.getIdentityService().setAuthenticatedUserId(USER_STARTER);
		Map<String,Object> val= Maps.newHashMap("next","default-user");
		ProcessInstance instance=coreBase.getRuntimeService()
				.startProcessInstanceByKeyAndTenantId(PROCESS_KEY,val,TENANT_ID);
		log.info("{} started {}",instance.getStartUserId(),instance.getProcessDefinitionName());
		log.info("processInstanceId:{}",instance.getId());
	}

	@Test
	public void changeApplyUserAndSubmit(){
		Task task=coreBase.getTaskService().createTaskQuery().processInstanceId(processInstanceId).singleResult();
		coreBase.getTaskService().setAssignee(task.getId(),USER_STARTER);
		Map<String,Object> val=new HashMap<>();
		val.put("next1","default-user-1");
		val.put("next2","default-user-2");
		coreBase.getTaskService().complete(task.getId(),val);

		//find next tasks
		List<Task> tasks=coreBase.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		log.info("next tasks");
		for (Task task1 : tasks) {
			log.info("Task {} ,assignee:{}",task1.getName(),task1.getAssignee());
		}
	}


	@Test
	public void updateTaskAssign(){
		List<Task> tasks=coreBase.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		for (int i = 0; i < tasks.size(); i++) {
			Task task=tasks.get(i);
			log.info("Task:{} ,old-assignee:{}",task.getName(),task.getAssignee());
			coreBase.getTaskService().setAssignee(task.getId(),USER_HANDLER+i);
			task.setAssignee(USER_HANDLER+i);
		}
		for (Task task : tasks) {
			log.info("Task:{} ,new-assignee:{}",task.getName(),task.getAssignee());
		}
	}

	@Test
	public void completeParallelTask(){
		List<Task> tasks=coreBase.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task task : tasks) {
			coreBase.getTaskService().complete(task.getId());
		}
	}

	@Test
	public void setLastAssignAndComplete(){
		Task task=coreBase.getTaskService().createTaskQuery().processInstanceId(processInstanceId).singleResult();
		log.info("old assignee:{}",task.getAssignee());
		coreBase.getTaskService().setAssignee(task.getId(),USER_HANDLER);
		log.info("current assignee:{}",USER_HANDLER);
		coreBase.getTaskService().complete(task.getId());
		log.info("{} completed task:{}",USER_HANDLER,task.getName());
	}

}
