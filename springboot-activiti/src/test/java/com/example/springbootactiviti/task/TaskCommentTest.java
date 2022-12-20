package com.example.springbootactiviti.task;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
public class TaskCommentTest {

	@Autowired
	private ActivitiCoreBase coreBase;

	@Test
	void testAddComment(){
		String taskId="";
		String instanceId="";
		Authentication.setAuthenticatedUserId("testUser");
		Comment comment=coreBase.getTaskService().addComment(taskId,instanceId,"some comments");
		log.info("comment user:{}",comment.getUserId());
		log.info("comments:{}",comment.getFullMessage());
	}

	@Test
	void testQueryComment(){
		String taskId="";
		Task task=coreBase.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		String instanceId;
		if(Objects.isNull(task)){
			HistoricTaskInstance hti=coreBase.getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			instanceId=hti.getProcessInstanceId();
		}else {
			instanceId=task.getProcessInstanceId();
		}
		List<HistoricActivityInstance> hisList=coreBase.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId(instanceId)
				.activityType(BpmnXMLConstants.ELEMENT_TASK_USER)
				.list();
		hisList.forEach(item->{
			List<Comment> comments=coreBase.getTaskService().getTaskComments(item.getTaskId());
			comments.forEach(System.out::println);
		});
	}
}
