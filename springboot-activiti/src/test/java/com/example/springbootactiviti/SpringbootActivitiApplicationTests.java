package com.example.springbootactiviti;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootActivitiApplicationTests {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;

	//部署流程-方式1
	@Test
	public void deployByBpmnFile() {
		Deployment deployment=repositoryService.createDeployment()
				.name("helloWorld")
				.addClasspathResource("processes/leave.bpmn")
				.deploy();
		System.out.println(deployment.getId()+"-->"+deployment.getName());
		//删除流程
//		String deployId=deployment.getId();
//		repositoryService.deleteDeployment(deployId,true);
	}

	//部署流程-方式2
	@Test
	public void deployByZip() throws IOException{
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("processes/myProcess.zip");
		ZipInputStream zipInputStream=new ZipInputStream(in);
		Deployment deployment=repositoryService.createDeployment()
				.name("helloworld-zip")
				.addZipInputStream(zipInputStream)
				.deploy();
		System.out.println(deployment.getId()+"-->"+deployment.getName());
		zipInputStream.close();
	}

	/**
	 * 使用String字符串部署，并且没有坐标布局
	 */
	@Test
	public void deployByStringWithoutLayout() throws UnsupportedEncodingException {
//		repositoryService.deleteDeployment("20001",true);
		//将bpmnXml转换成bpmnModel
		String xmlStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/processdef\"> <process id=\"process_1569394250427\" name=\"process_1569394250427\" isExecutable=\"true\">  <startEvent id=\"mx_2\" name=\"火警_1\"></startEvent>  <exclusiveGateway id=\"mx_3\" name=\"排他网关_1\"></exclusiveGateway>   <userTask id=\"mx_4\" name=\"摄像头_1\" activiti:assignee=\"${next}\">   </userTask>   <userTask id=\"mx_5\" name=\"短信_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_6\" sourceRef=\"mx_2\" targetRef=\"mx_3\"></sequenceFlow>  <sequenceFlow id=\"mx_7\" sourceRef=\"mx_3\" targetRef=\"mx_4\"></sequenceFlow>  <sequenceFlow id=\"mx_8\" sourceRef=\"mx_4\" targetRef=\"mx_9\"></sequenceFlow>  <exclusiveGateway id=\"mx_9\" name=\"排他网关_2\"></exclusiveGateway>  <sequenceFlow id=\"mx_10\" sourceRef=\"mx_9\" targetRef=\"mx_5\"></sequenceFlow>   <userTask id=\"mx_11\" name=\"结束事件_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_12\" sourceRef=\"mx_5\" targetRef=\"mx_11\"></sequenceFlow> </process></definitions>";
		InputStream is=new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
		BpmnXMLConverter bpmnXMLConverter=new BpmnXMLConverter();
		InputStreamProvider provider=new InputStreamSource(is);
		BpmnModel bpmnModel=bpmnXMLConverter.convertToBpmnModel(provider,true,false,"UTF-8");
		//2.Generate graphical information
		new BpmnAutoLayout(bpmnModel).execute();
		Deployment deployment=repositoryService.createDeployment()
				.name("火警报警")
				.category("申请类业务")
				.addBpmnModel("fireflow.bpmn20.xml",bpmnModel)
//                .addString("fireflow.bomn20.xml",xmlStr)
				.deploy();
		System.out.println("部署ID>>"+deployment.getId());
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
