package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.validation.ValidationError;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipInputStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeploymentTest {

	@Autowired
	private ActivitiCoreBase coreBase;
	private static final String DEPLOYMENT_TENANT = "TEST";
	private static final String DEPLOYMENT_CATEGORY = "测试";

	private void log(Deployment deployment) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("部署ID:{}", deployment.getId());
		log.info("部署名称:{}", deployment.getName());
		log.info("部署时间:{}", sdf.format(deployment.getDeploymentTime()));
		log.info("部署租户:{}", deployment.getTenantId());
		log.info("部署分类:{}", deployment.getCategory());
		log.info("部署密钥:{}", deployment.getKey());
	}

	/**
	 * 部署流程-BPMN文件
	 */
	@Test
	public void deployByBpmnFile() {
		Deployment deployment = coreBase.getRepositoryService().createDeployment()
				.name("请假申请测试")
				.category(DEPLOYMENT_CATEGORY)
				.tenantId(DEPLOYMENT_TENANT)
				.addClasspathResource("processes/leave.bpmn")
				.deploy();
		log(deployment);
	}

	/**
	 * 部署流程-ZIP
	 */
	@Test
	public void deployByZip() throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/myProcess.zip");
		assert in != null;
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = coreBase.getRepositoryService().createDeployment()
				.name("helloworld-zip")
				.category("测试")
				.tenantId("test")
				.addZipInputStream(zipInputStream)
				.deploy();
		zipInputStream.close();
		log(deployment);
	}

	/**
	 * 部署流程-xml字符串
	 */
	@Test
	public void deployByStr() {
		String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:activiti=\"http://activiti.org/bpmn\" id=\"sample-diagram\" targetNamespace=\"http://activiti.org/bpmn\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\">\n" +
				"  <bpmn2:process id=\"physics_model_audit\" name=\"model_audit\" isExecutable=\"true\">\n" +
				"    <bpmn2:startEvent id=\"StartEvent_1\" name=\"开始\">\n" +
				"      <bpmn2:extensionElements />\n" +
				"      <bpmn2:outgoing>Flow_0udykh1</bpmn2:outgoing>\n" +
				"    </bpmn2:startEvent>\n" +
				"    <bpmn2:userTask id=\"Activity_0icgl19\" name=\"任务1\" activiti:candidateUsers=\"admin\">\n" +
				"      <bpmn2:extensionElements />\n" +
				"      <bpmn2:incoming>Flow_0udykh1</bpmn2:incoming>\n" +
				"      <bpmn2:outgoing>Flow_00h1i84</bpmn2:outgoing>\n" +
				"    </bpmn2:userTask>\n" +
				"    <bpmn2:sequenceFlow id=\"Flow_0udykh1\" sourceRef=\"StartEvent_1\" targetRef=\"Activity_0icgl19\" />\n" +
				"    <bpmn2:userTask id=\"Activity_1k5cw38\" name=\"任务2\" activiti:candidateUsers=\"karven\">\n" +
				"      <bpmn2:extensionElements />\n" +
				"      <bpmn2:incoming>Flow_00h1i84</bpmn2:incoming>\n" +
				"      <bpmn2:outgoing>Flow_1wq8e0n</bpmn2:outgoing>\n" +
				"    </bpmn2:userTask>\n" +
				"    <bpmn2:sequenceFlow id=\"Flow_00h1i84\" sourceRef=\"Activity_0icgl19\" targetRef=\"Activity_1k5cw38\" />\n" +
				"    <bpmn2:endEvent id=\"Event_1kbbrrv\" name=\"结束\">\n" +
				"      <bpmn2:incoming>Flow_1wq8e0n</bpmn2:incoming>\n" +
				"    </bpmn2:endEvent>\n" +
				"    <bpmn2:sequenceFlow id=\"Flow_1wq8e0n\" sourceRef=\"Activity_1k5cw38\" targetRef=\"Event_1kbbrrv\" />\n" +
				"  </bpmn2:process>\n" +
				"  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
				"    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"physics_model_audit\">\n" +
				"      <bpmndi:BPMNEdge id=\"Flow_0udykh1_di\" bpmnElement=\"Flow_0udykh1\">\n" +
				"        <di:waypoint x=\"186\" y=\"168\" />\n" +
				"        <di:waypoint x=\"240\" y=\"168\" />\n" +
				"      </bpmndi:BPMNEdge>\n" +
				"      <bpmndi:BPMNEdge id=\"Flow_00h1i84_di\" bpmnElement=\"Flow_00h1i84\">\n" +
				"        <di:waypoint x=\"340\" y=\"168\" />\n" +
				"        <di:waypoint x=\"400\" y=\"168\" />\n" +
				"      </bpmndi:BPMNEdge>\n" +
				"      <bpmndi:BPMNEdge id=\"Flow_1wq8e0n_di\" bpmnElement=\"Flow_1wq8e0n\">\n" +
				"        <di:waypoint x=\"500\" y=\"168\" />\n" +
				"        <di:waypoint x=\"562\" y=\"168\" />\n" +
				"      </bpmndi:BPMNEdge>\n" +
				"      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
				"        <dc:Bounds x=\"150\" y=\"150\" width=\"36\" height=\"36\" />\n" +
				"        <bpmndi:BPMNLabel>\n" +
				"          <dc:Bounds x=\"157\" y=\"193\" width=\"23\" height=\"14\" />\n" +
				"        </bpmndi:BPMNLabel>\n" +
				"      </bpmndi:BPMNShape>\n" +
				"      <bpmndi:BPMNShape id=\"Activity_0icgl19_di\" bpmnElement=\"Activity_0icgl19\">\n" +
				"        <dc:Bounds x=\"240\" y=\"128\" width=\"100\" height=\"80\" />\n" +
				"        <bpmndi:BPMNLabel />\n" +
				"      </bpmndi:BPMNShape>\n" +
				"      <bpmndi:BPMNShape id=\"Activity_1k5cw38_di\" bpmnElement=\"Activity_1k5cw38\">\n" +
				"        <dc:Bounds x=\"400\" y=\"128\" width=\"100\" height=\"80\" />\n" +
				"        <bpmndi:BPMNLabel />\n" +
				"      </bpmndi:BPMNShape>\n" +
				"      <bpmndi:BPMNShape id=\"Event_1kbbrrv_di\" bpmnElement=\"Event_1kbbrrv\">\n" +
				"        <dc:Bounds x=\"562\" y=\"150\" width=\"36\" height=\"36\" />\n" +
				"        <bpmndi:BPMNLabel>\n" +
				"          <dc:Bounds x=\"569\" y=\"193\" width=\"23\" height=\"14\" />\n" +
				"        </bpmndi:BPMNLabel>\n" +
				"      </bpmndi:BPMNShape>\n" +
				"    </bpmndi:BPMNPlane>\n" +
				"  </bpmndi:BPMNDiagram>\n" +
				"</bpmn2:definitions>\n";
		Deployment deployment = coreBase.getRepositoryService().createDeployment()
				.name("普通流程")
				.category(DEPLOYMENT_CATEGORY)
				.tenantId(DEPLOYMENT_TENANT)
				.addString("simpleTask.bpmn", xmlString)
				.deploy();
		log(deployment);
	}

	/**
	 * 使用String字符串部署，并自动生成坐标布局
	 */
	@Test
	public void deployByStringWithoutLayout() throws UnsupportedEncodingException {
//		repositoryService.deleteDeployment("20001",true);
		//将bpmnXml转换成bpmnModel
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/processdef\"> <process id=\"process_1569394250427\" name=\"process_1569394250427\" isExecutable=\"true\">  <startEvent id=\"mx_2\" name=\"火警_1\"></startEvent>  <exclusiveGateway id=\"mx_3\" name=\"排他网关_1\"></exclusiveGateway>   <userTask id=\"mx_4\" name=\"摄像头_1\" activiti:assignee=\"${next}\">   </userTask>   <userTask id=\"mx_5\" name=\"短信_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_6\" sourceRef=\"mx_2\" targetRef=\"mx_3\"></sequenceFlow>  <sequenceFlow id=\"mx_7\" sourceRef=\"mx_3\" targetRef=\"mx_4\"></sequenceFlow>  <sequenceFlow id=\"mx_8\" sourceRef=\"mx_4\" targetRef=\"mx_9\"></sequenceFlow>  <exclusiveGateway id=\"mx_9\" name=\"排他网关_2\"></exclusiveGateway>  <sequenceFlow id=\"mx_10\" sourceRef=\"mx_9\" targetRef=\"mx_5\"></sequenceFlow>   <userTask id=\"mx_11\" name=\"结束事件_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_12\" sourceRef=\"mx_5\" targetRef=\"mx_11\"></sequenceFlow> </process></definitions>";
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));

		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		InputStreamProvider provider = new InputStreamSource(is);
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(provider, true, false, StandardCharsets.UTF_8.name());
		//2.Generate graphical information
		new BpmnAutoLayout(bpmnModel).execute();
		Deployment deployment = coreBase.getRepositoryService().createDeployment()
				.name("fire-alarm")
				.category(DEPLOYMENT_CATEGORY)
				.tenantId(DEPLOYMENT_TENANT)
				.addBpmnModel("fireflow.bpmn20.xml", bpmnModel)
				.deploy();
		log(deployment);
	}

	/**
	 * 获取流程图
	 */
	@Test
	public void getDeployedPngByXML() throws Exception {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
		String processDefineKey = "process_1574668599920";
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		InputStreamProvider provider = new InputStreamSource(is);
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(provider, true, false, "UTF-8");
		//Generate graphical information
		new BpmnAutoLayout(bpmnModel).execute();
		Deployment deployment = coreBase.getRepositoryService().createDeployment()
				.addBpmnModel("dynamic-model.bpmn", bpmnModel)
				.name("Dynamic process deployment")
				.deploy();
		Map<String, Object> val = new HashMap<>();
		val.put("next", "wuwh");
		ProcessInstance instance = coreBase.getRuntimeService().startProcessInstanceByKey(processDefineKey, val);
		InputStream processDiagram = coreBase.getRepositoryService().getProcessDiagram(instance.getProcessDefinitionId());
		FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));
		//删除部署
		coreBase.getRepositoryService().deleteDeployment(deployment.getId(), true);
	}

	/**
	 * 删除部署
	 */
	@Test
	public void removeSingleDeployed() {
		String deployId = "122501";
		coreBase.getRepositoryService().deleteDeployment(deployId, true);
	}

	/**
	 * 删除所有部署
	 */
	@Test
	public void removeAllDeployed() {
		List<Deployment> list = coreBase.getRepositoryService().createDeploymentQuery().list();
		list.forEach(item -> {
			coreBase.getRepositoryService().deleteDeployment(item.getId(), true);
			log.info("流程部署:<{}> 已删除", item.getName());
		});
	}

	@Test
	public void processValidation() throws XMLStreamException {
		String processString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/processdef\"> <process id=\"process_1569394250427\" name=\"process_1569394250427\" isExecutable=\"true\">  <startEvent id=\"mx_2\" name=\"火警_1\"></startEvent>  <exclusiveGateway id=\"mx_3\" name=\"排他网关_1\"></exclusiveGateway>   <userTask id=\"mx_4\" name=\"摄像头_1\" activiti:assignee=\"${next}\">   </userTask>   <userTask id=\"mx_5\" name=\"短信_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_6\" sourceRef=\"mx_2\" targetRef=\"mx_3\"></sequenceFlow>  <sequenceFlow id=\"mx_7\" sourceRef=\"mx_3\" targetRef=\"mx_4\"></sequenceFlow>  <sequenceFlow id=\"mx_8\" sourceRef=\"mx_4\" targetRef=\"mx_9\"></sequenceFlow>  <exclusiveGateway id=\"mx_9\" name=\"排他网关_2\"></exclusiveGateway>  <sequenceFlow id=\"mx_10\" sourceRef=\"mx_9\" targetRef=\"mx_5\"></sequenceFlow>   <userTask id=\"mx_11\" name=\"结束事件_1\" activiti:assignee=\"${next}\">   </userTask>  <sequenceFlow id=\"mx_12\" sourceRef=\"mx_5\" targetRef=\"mx_11\"></sequenceFlow> </process></definitions>";
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(processString.getBytes()), StandardCharsets.UTF_8);
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
		List<ValidationError> errors = coreBase.getRepositoryService().validateProcess(bpmnModel);
		if (errors.size() == 0) {
			log.info("process validate success,no errors found");
		} else {
			errors.forEach(error -> {
				log.error(errors.toString());
			});
		}
	}
}
