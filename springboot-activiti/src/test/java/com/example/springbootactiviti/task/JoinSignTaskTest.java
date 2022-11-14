package com.example.springbootactiviti.task;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 会签流程测试
 */
@Slf4j
@SpringBootTest
public class JoinSignTaskTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	@Test
	public void taskStart() {
		Map<String, Object> vars = Maps.newHashMap("hqList", "bob,alice");
		String processInstanceKey = "joinsign";
		String tenantId = "TEST";
		ProcessInstance processInstance = coreBase.getRuntimeService().createProcessInstanceBuilder()
				.processDefinitionKey(processInstanceKey)
				.tenantId(tenantId)
				.name("会签任务")
				.variables(vars)
				.start();
		System.out.println("租户:" + processInstance.getTenantId() + "  ,启动流程:" + processInstance.getName());
	}

	@Test
	public void joinSignComplete() {
		List<String> joinSignList = Arrays.asList("11", "22", "33");
		List<Task> listOfTask = coreBase.getTaskService().createTaskQuery().taskTenantId("TEST").taskAssigneeIds(joinSignList).list();
		for (Task task : listOfTask) {
			System.out.println("==========分割线==========");
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务名称:" + task.getName());
			System.out.println("任务描述:" + task.getDescription());
			System.out.println("任务的创建时间:" + task.getCreateTime());
			System.out.println("任务的办理人:" + task.getAssignee());
			System.out.println("流程实例ID：" + task.getProcessInstanceId());
			System.out.println("执行对象ID:" + task.getExecutionId());
			System.out.println("流程定义ID:" + task.getProcessDefinitionId());
			System.out.println("本次任务变量:" + task.getTaskLocalVariables());
			System.out.println("流程定义的变量" + task.getProcessVariables());
			coreBase.getTaskService().complete(task.getId());
		}
	}

	@Test
	public void taskComplete() {
		Task task = coreBase.getTaskService().createTaskQuery().taskAssignee("admin").singleResult();
		coreBase.getTaskService().complete(task.getId());
	}
}
