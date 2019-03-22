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
public class SpringbootActivitiApplicationTests {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;

	//部署流程-方式1
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
