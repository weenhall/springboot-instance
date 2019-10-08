package com.example.springbootactiviti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDeployTests {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;

	//部署流程-BPMN文件
	@Test
	public void deployByBpmn() {
		Deployment deployment=repositoryService.createDeployment()
				.name("helloWorld")
				.addClasspathResource("processes/leave.bpmn")
				.deploy();
		System.out.println(deployment.getId()+"-->"+deployment.getName());
		//删除流程
//		String deployId=deployment.getId();
//		repositoryService.deleteDeployment(deployId,true);
	}

	//部署流程-ZIP
	@Test
	public void deployByZip() throws IOException{
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("processes/myProcess.zip");
		ZipInputStream zipInputStream=new ZipInputStream(in);
		Deployment deployment=repositoryService.createDeployment()
				.name("helloworld-zip")
				.addZipInputStream(zipInputStream)
				.deploy();
		zipInputStream.close();
	}

	//部署流程-xml字符串
	@Test
	public void deployByStr(){
		String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
				"<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"EmployeeAskForLeave\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1560906560946\" name=\"\" targetNamespace=\"EmployeeAskForLeave\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\">\n" +
				"  <process id=\"leaveProcess\" isClosed=\"false\" isExecutable=\"true\" processType=\"None\">\n" +
				"    <startEvent id=\"_2\" name=\"StartEvent\"/>\n" +
				"    <userTask activiti:assignee=\"wwh\" activiti:exclusive=\"true\" id=\"_3\" name=\"apply\">\n" +
				"            <documentation>\t             \n" +
				"               {\"name\":\"hellow\",\"tel\":\"1519727\"}\n" +
				"            </documentation>\n" +
				"    </userTask>\n" +
				"    <sequenceFlow id=\"_4\" sourceRef=\"_2\" targetRef=\"_3\"/>\n" +
				"    <exclusiveGateway gatewayDirection=\"Unspecified\" id=\"_5\" name=\"ExclusiveGateway\"/>\n" +
				"    <sequenceFlow id=\"_6\" sourceRef=\"_3\" targetRef=\"_5\"/>\n" +
				"    <userTask activiti:assignee=\"wwh\" activiti:exclusive=\"true\" id=\"_7\" name=\"approve\"/>\n" +
				"    <sequenceFlow id=\"_8\" sourceRef=\"_5\" targetRef=\"_7\"/>\n" +
				"    <endEvent id=\"_9\" name=\"EndEvent\"/>\n" +
				"    <sequenceFlow id=\"_10\" sourceRef=\"_7\" targetRef=\"_9\"/>\n" +
				"  </process> \n" +
				"</definitions>";
		Deployment deployment=repositoryService.createDeployment()
				.name("请假流程")
				.category("审批业务类")
				.addString("helloworld.bpmn",xmlString)
				.deploy();
		System.out.println(deployment.getId()+"-->"+deployment.getName());
	}

	@Test
	public void removeDeployById(){
		repositoryService.deleteDeployment("5001",true);
	}

	//启动流程实例(每个实例相当于打开一张申请单)
	@Test
	public void processInstanceStart(){
		String processDefiniteKey="leaveProcess";
		ProcessInstance instance=runtimeService.startProcessInstanceByKey(processDefiniteKey);
		System.out.println("流程实例ID:"+instance.getId());//流程实例ID
		System.out.println("流程定义ID:"+instance.getProcessDefinitionId());//流程定义ID
	}

	//查询流程实例
	@Test
	public void queryProcessInstance(){
		String processDefiniteKey="leaveProcess";
		ProcessInstance instance=runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefiniteKey)
				.singleResult();
		System.out.println("流程实例ID:"+instance.getId());//流程实例ID
		System.out.println("流程定义ID:"+instance.getProcessDefinitionId());//流程定义ID
	}

	//删除流程实例
	@Test
	public void deleteProcessInstance(){
		String processDefiniteKey="leaveProcess";
		ProcessInstance instance=runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefiniteKey)
				.singleResult();
		String processInstancId=instance.getProcessInstanceId();
		runtimeService.deleteProcessInstance(processInstancId,"删除测试");
	}


}
