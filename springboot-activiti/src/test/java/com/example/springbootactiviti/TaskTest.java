package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@Slf4j
@SpringBootTest
public class TaskTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	@Test
	public void taskQuery(){
		TaskQuery taskQuery=coreBase.getTaskService().createTaskQuery();
	}

	/**
	 * 我的待办
	 */
	@Test
	public void myTodo(){
		String currentUser="admin";
		TaskQuery taskQuery=coreBase.getTaskService().createTaskQuery();
		taskQuery.taskAssignee(currentUser);
		taskQuery.active();
		List<Task> myTask=taskQuery.list();
		myTask.forEach(item->{
			if(StringUtils.isEmpty(item.getAssignee())){
				log.info("任务:{} 待签收",item.getName());
			}else{
				log.info("任务:{} 待办理",item.getName());
			}
		});
	}

	/**
	 * 我的待签收
	 */
	@Test
	public void myTobeClaimed(){
		String currentUser="";
		String currentUserGroup="";
		List<Task> candidateTask=coreBase.getTaskService().createTaskQuery()
				.taskCandidateUser(currentUser)
				.active()
				.list();
		List<Task> candidateGroupTask=coreBase.getTaskService().createTaskQuery()
				.taskCandidateGroup(currentUserGroup)
				.active()
				.list();
	}
}
