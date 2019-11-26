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
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDeploy {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;

	//部署流程-BPMN文件
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
	public void deployByStr() {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
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
		Deployment deployment = repositoryService.createDeployment()
				.name("请假流程")
				.category("审批业务类")
				.addString("helloworld.bpmn", xmlString)
				.deploy();
		System.out.println(deployment.getId() + "-->" + deployment.getName());
	}

	/**
	 * 使用String字符串部署，并自动生成坐标布局
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

	/**
	 * 获取流程图
	 */
	@Test
	public void getDeployedPngByXML() throws Exception{
		String xmlStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/processdef\">\n" +
				" <process id=\"process_1574668599920\" name=\"process_1574668599920\" isExecutable=\"true\">\n" +
				"  <startEvent id=\"mx_2\" name=\"火警_1\"></startEvent>\n" +
				"  <parallelGateway id=\"mx_3\" name=\"并行网关_1\"></parallelGateway>\n" +
				"  <userTask id=\"mx_4\" name=\"工业电视_1\" activiti:assignee=\"${next}\">\n" +
				"    <documentation>[{\"Type\":\"camera\"},{\"DeviceId\":\"1000000$1$0$10\"}]</documentation>\n" +
				"  </userTask>\n" +
				"  <userTask id=\"mx_5\" name=\"工业电视_2\" activiti:assignee=\"${next}\">\n" +
				"    <documentation>[{\"Type\":\"camera\"},{\"DeviceId\":\"1000000$1$0$13\"}]</documentation>\n" +
				"  </userTask>\n" +
				"  <userTask id=\"mx_6\" name=\"工业电视_3\" activiti:assignee=\"${next}\">\n" +
				"    <documentation>[{\"Type\":\"camera\"},{\"DeviceId\":\"1000000$1$0$15\"}]</documentation>\n" +
				"  </userTask>\n" +
				"  <parallelGateway id=\"mx_7\" name=\"并行网关_2\"></parallelGateway>\n" +
				"  <endEvent id=\"mx_8\" ></endEvent>\n" +
				"  <sequenceFlow id=\"mx_9\" sourceRef=\"mx_2\" targetRef=\"mx_3\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_10\" sourceRef=\"mx_3\" targetRef=\"mx_4\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_11\" sourceRef=\"mx_3\" targetRef=\"mx_5\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_12\" sourceRef=\"mx_3\" targetRef=\"mx_6\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_13\" sourceRef=\"mx_4\" targetRef=\"mx_7\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_14\" sourceRef=\"mx_5\" targetRef=\"mx_7\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_15\" sourceRef=\"mx_6\" targetRef=\"mx_7\"></sequenceFlow>\n" +
				"  <sequenceFlow id=\"mx_16\" sourceRef=\"mx_7\" targetRef=\"mx_8\"></sequenceFlow>\n" +
				" </process>\n" +
				"</definitions>";
		String processDefineKey="process_1574668599920";
		InputStream is=new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
		BpmnXMLConverter bpmnXMLConverter=new BpmnXMLConverter();
		InputStreamProvider provider=new InputStreamSource(is);
		BpmnModel bpmnModel=bpmnXMLConverter.convertToBpmnModel(provider,true,false,"UTF-8");
		//Generate graphical information
		new BpmnAutoLayout(bpmnModel).execute();
		Deployment deployment=repositoryService.createDeployment()
				.addBpmnModel("dynamic-model.bpmn",bpmnModel)
				.name("Dynamic process deployment")
				.deploy();
		Map<String, Object> val = new HashMap<>();
		val.put("next","wuwh");
		ProcessInstance instance=runtimeService.startProcessInstanceByKey(processDefineKey,val);
		InputStream processDiagram=repositoryService.getProcessDiagram(instance.getProcessDefinitionId());
		FileUtils.copyInputStreamToFile(processDiagram,new File("target/diagram.png"));
		repositoryService.deleteDeployment(deployment.getId(),true);//删除测试
	}

	@Test
	public void removeDeployes(){
		String [] deployedIds=new String[]{"10001"};
		for (int i = 0; i < deployedIds.length; i++) {
			repositoryService.deleteDeployment(deployedIds[i],true);//级联删除流程有关的所有数据
		}
	}
}
