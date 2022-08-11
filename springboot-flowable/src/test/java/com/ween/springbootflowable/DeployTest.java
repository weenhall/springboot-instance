package com.ween.springbootflowable;

import liquibase.pro.packaged.B;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.io.InputStreamProvider;
import org.flowable.common.engine.impl.util.io.InputStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.zip.ZipInputStream;

@Slf4j
@SpringBootTest
public class DeployTest {

	@Autowired
	private RepositoryService repositoryService;

	private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String DEFAULT_DEPLOY_NAME="TEST";
	private final static String DEFAULT_DEPLOY_CATEGORY="TEST";
	private final static String DEFAULT_DEPLOY_TENANT_ID="TEST";
	private final static String DEFAULT_DEPLOY_KEY="abc123";

	private void log(Deployment deployment){
		log.info("部署ID:{}", deployment.getId());
		log.info("部署名称:{}", deployment.getName());
		log.info("部署时间:{}", sdf.format(deployment.getDeploymentTime()));
		log.info("部署租户:{}", deployment.getTenantId());
		log.info("部署分类:{}", deployment.getCategory());
		log.info("部署密钥:{}", deployment.getKey());
	}

	@Test
	public void deployByBpmnFile(){
		Deployment deployment=repositoryService.createDeployment()
				.name(DEFAULT_DEPLOY_NAME)
				.category(DEFAULT_DEPLOY_CATEGORY)
				.tenantId(DEFAULT_DEPLOY_TENANT_ID)
				.addClasspathResource("processes/sample.bpmn")
				.key(DEFAULT_DEPLOY_KEY)
				.deploy();
		log(deployment);
		setProcessDefinitionCategory(deployment.getId());
	}

	@Test
	public void deployByZipFile(){
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("processes/sample.zip");
		assert in!=null;
		ZipInputStream zip=new ZipInputStream(in);
		Deployment deployment=repositoryService.createDeployment()
				.name(DEFAULT_DEPLOY_NAME)
				.category(DEFAULT_DEPLOY_CATEGORY)
				.tenantId(DEFAULT_DEPLOY_TENANT_ID)
				.addZipInputStream(zip)
				.key(DEFAULT_DEPLOY_KEY)
				.deploy();
		log(deployment);
		setProcessDefinitionCategory(deployment.getId());
	}

	@Test
	public void deployByXmlString(){
		String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<definitions\n" +
				"        xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"\n" +
				"        xmlns:flowable=\"http://flowable.org/bpmn\"\n" +
				"        targetNamespace=\"Examples\">\n" +
				"\n" +
				"    <process id=\"oneTaskProcess\" name=\"The One Task Process\">\n" +
				"        <startEvent id=\"theStart\" />\n" +
				"        <sequenceFlow id=\"flow1\" sourceRef=\"theStart\" targetRef=\"theTask\" />\n" +
				"        <userTask id=\"theTask\" name=\"my task\" flowable:assignee=\"kermit\" />\n" +
				"        <sequenceFlow id=\"flow2\" sourceRef=\"theTask\" targetRef=\"theEnd\" />\n" +
				"        <endEvent id=\"theEnd\" />\n" +
				"    </process>\n" +
				"\n" +
				"</definitions>";
		Deployment deployment=repositoryService.createDeployment()
				.name(DEFAULT_DEPLOY_NAME)
				.category(DEFAULT_DEPLOY_CATEGORY)
				.tenantId(DEFAULT_DEPLOY_TENANT_ID)
				.addString("sample.bpmn",xmlString)
				.key(DEFAULT_DEPLOY_KEY)
				.deploy();
		log(deployment);
		setProcessDefinitionCategory(deployment.getId());
	}

	@Test
	public void deployByXmlStringWithoutLayout(){
		String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<definitions\n" +
				"        xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"\n" +
				"        xmlns:flowable=\"http://flowable.org/bpmn\"\n" +
				"        targetNamespace=\"Examples\">\n" +
				"\n" +
				"    <process id=\"oneTaskProcess\" name=\"The One Task Process\">\n" +
				"        <startEvent id=\"theStart\" />\n" +
				"        <sequenceFlow id=\"flow1\" sourceRef=\"theStart\" targetRef=\"theTask\" />\n" +
				"        <userTask id=\"theTask\" name=\"my task\" flowable:assignee=\"kermit\" />\n" +
				"        <sequenceFlow id=\"flow2\" sourceRef=\"theTask\" targetRef=\"theEnd\" />\n" +
				"        <endEvent id=\"theEnd\" />\n" +
				"    </process>\n" +
				"\n" +
				"</definitions>";
		InputStream in=new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
		BpmnXMLConverter converter=new BpmnXMLConverter();
		InputStreamProvider provider=new InputStreamSource(in);
		BpmnModel model=converter.convertToBpmnModel(provider,true,false,StandardCharsets.UTF_8.name());
		new BpmnAutoLayout(model).execute();
		Deployment deployment=repositoryService.createDeployment()
				.name(DEFAULT_DEPLOY_NAME)
				.category(DEFAULT_DEPLOY_CATEGORY)
				.tenantId(DEFAULT_DEPLOY_TENANT_ID)
				.addBpmnModel("sample.bpmn",model)
				.key(DEFAULT_DEPLOY_KEY)
				.deploy();
		log(deployment);
		setProcessDefinitionCategory(deployment.getId());
	}

	@Test
	public void removeDeployment(){
		String deploymentId="";
		boolean cascade=true;
		repositoryService.deleteDeployment(deploymentId,true);
	}

	private void setProcessDefinitionCategory(String deploymentId){
		ProcessDefinition definition=repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).latestVersion().singleResult();
		repositoryService.setProcessDefinitionCategory(definition.getId(), DeployTest.DEFAULT_DEPLOY_CATEGORY);
	}
}
