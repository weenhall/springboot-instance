package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicProcessGenerate {

    @Autowired
    ActivitiCoreBase activitiCoreBase;

    @Test
    public void testDynamicDeploy() throws IOException {
        //1.Build up the modal from scratch
        BpmnModel model=new BpmnModel();
        Process process=new Process();
        model.addProcess(process);
        process.setId("myProcess");//bpmn中流程定义的key

        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1","First Task","zhangshan"));
        process.addFlowElement(createUserTask("task2","Second Task","lisi"));
        process.addFlowElement(createEndEvent());

        //sequenceFlow
        process.addFlowElement(createSequenceFlow("start","task1"));
        process.addFlowElement(createSequenceFlow("task1","task2"));
        process.addFlowElement(createSequenceFlow("task2","end"));

        //2.Generate graphical information
        new BpmnAutoLayout(model).execute();

        //3.Deploy the process to the engine
        Deployment deployment=activitiCoreBase.getRepositoryService().createDeployment()
                .addBpmnModel("dynamic-model.bpmn",model)
                .name("Dynamic process deployment")
                .deploy();
        //4.Start a process instance
        ProcessInstance instance=activitiCoreBase.getRuntimeService().startProcessInstanceByKey("myProcess");
        //5.Check if task is avaliable
        List<Task> listOfTask=activitiCoreBase.getTaskService().createTaskQuery().processInstanceId(instance.getId()).list();
        Assert.assertEquals(1,listOfTask.size());
        Assert.assertEquals("First Task",listOfTask.get(0).getName());
        Assert.assertEquals("zhangshan",listOfTask.get(0).getAssignee());

        //6.Save process diagram to a file
        InputStream processDiagram=activitiCoreBase.getRepositoryService().getProcessDiagram(instance.getProcessDefinitionId());
        FileUtils.copyInputStreamToFile(processDiagram,new File("target/diagram.png"));
    }

    @Test
    public void testDynamicImage() throws IOException {
        RepositoryService repositoryService=activitiCoreBase.getRepositoryService();
        String deploymentId="22501";//act_re_deployment
        List<String> names=repositoryService.getDeploymentResourceNames(deploymentId);
        String imageName=null;
        for(String name :names){
            if(name.indexOf(".png")>=0){
                imageName=name;
            }
        }
        Optional<String> opt=Optional.ofNullable(imageName);
        if(opt.isPresent()){
            File file=new File("target/dynamic.png");
            InputStream inputStream=repositoryService.getResourceAsStream(deploymentId,imageName);
            FileUtils.copyInputStreamToFile(inputStream,file);
        }
    }

    protected StartEvent createStartEvent(){
        StartEvent startEvent=new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    protected EndEvent createEndEvent(){
        EndEvent endEvent=new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }

    protected SequenceFlow createSequenceFlow(String from,String to){
        SequenceFlow flow=new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    protected UserTask createUserTask(String id,String name,String assignee){
        UserTask task=new UserTask();
        task.setId(id);
        task.setName(name);
        task.setAssignee(assignee);
        return task;
    }
}
