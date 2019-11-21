package com.example.springbootactiviti;

import com.alibaba.fastjson.JSON;
import com.example.springbootactiviti.base.ActivitiCoreBase;
import org.activiti.engine.IdentityService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by ween on 2019/3/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiCoreApiTest {
    @Autowired
    ActivitiCoreBase activitiCoreBase;

    /**
     * IdentityService 测试
     */
    @Test
    public void addUser(){
        User user=activitiCoreBase.getIdentityService().newUser("wuwh");
        user.setFirstName("吴");
        user.setLastName("xx");
        user.setPassword("1234");
        activitiCoreBase.getIdentityService().saveUser(user);
    }

    @Test
    public void queryUser(){
        String userId="wuwh";
        User user=activitiCoreBase.getIdentityService().createUserQuery()
                .userId(userId)
                .singleResult();
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void deleteUser(){
        activitiCoreBase.getIdentityService().deleteUser("wuwh");
    }

    /**
     * HistoryService 测试
     */
    @Test
    public void HistoryProcessInstance() {
        List<HistoricProcessInstance> datas = activitiCoreBase.getHistoryService().createHistoricProcessInstanceQuery()
                .finished().list();
        for (HistoricProcessInstance historicProcessInstance : datas) {
            System.out.println("流程实例id: "+historicProcessInstance.getId());
            System.out.println("部署id: "+historicProcessInstance.getDeploymentId());
            System.out.println("结束event: "+historicProcessInstance.getEndActivityId());
            System.out.println("流程名称: "+historicProcessInstance.getName());
            System.out.println("PROC_DEF_ID: "+historicProcessInstance.getProcessDefinitionId());
            System.out.println("流程定义Key: "+historicProcessInstance.getProcessDefinitionKey());
            System.out.println("流程定义名称: "+historicProcessInstance.getProcessDefinitionName());
            System.out.println("开始event: "+historicProcessInstance.getStartActivityId());
        }
    }


    @Test
    public void listOfLatestProcessDefinition(){
        //挂起某个流程定义
        String processDefiniteKey="process_1573537843438";
//        activitiCoreBase.getRepositoryService().suspendProcessDefinitionByKey(processDefiniteKey);
        //根据流程定义的key查询流程定义
        List<ProcessDefinition> list=activitiCoreBase.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefiniteKey).list();
        System.out.println(list.size());
        //查询最新版本的流程定义
        List<ProcessDefinition> listOfAll=activitiCoreBase.getRepositoryService().createProcessDefinitionQuery().latestVersion().list();
        System.out.println("已部署的流程定义个数:"+listOfAll.size());
        listOfAll.forEach(pd->{
            System.out.println("id:"+pd.getId());
            System.out.println("name:"+pd.getName());
            System.out.println("key:"+pd.getKey());
            System.out.println("是否挂起:"+pd.isSuspended());
            System.out.println("version:"+pd.getVersion());
            System.out.println("=====================");
        });
    }

    /**
     * 通过流程定义key删除相关的流程部署所有关联
     */
    @Test
    public void removeDeployById(){
        String processDefiniteKey="process_1573695862791";
        List<ProcessDefinition> list=activitiCoreBase.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefiniteKey).list();
        list.forEach(pd->{
            activitiCoreBase.getRepositoryService().deleteDeployment(pd.getDeploymentId(),true);
        });
//		repositoryService.deleteDeployment("5002",true);
    }

    //启动流程实例(每个实例相当于打开一张申请单)
    @Test
    public void processInstanceStart(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().startProcessInstanceByKey(processDefiniteKey);
        System.out.println("流程实例ID:"+instance.getId());//流程实例ID
        System.out.println("流程定义ID:"+instance.getProcessDefinitionId());//流程定义ID
    }

    //查询流程实例
    @Test
    public void queryProcessInstance(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().createProcessInstanceQuery()
                .processDefinitionKey(processDefiniteKey)
                .singleResult();
        System.out.println("流程实例ID:"+instance.getId());//流程实例ID
        System.out.println("流程定义ID:"+instance.getProcessDefinitionId());//流程定义ID
    }

    //删除流程实例
    @Test
    public void deleteProcessInstance(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().createProcessInstanceQuery()
                .processDefinitionKey(processDefiniteKey)
                .singleResult();
        String processInstancId=instance.getProcessInstanceId();
        activitiCoreBase.getRuntimeService().deleteProcessInstance(processInstancId,"删除测试");
    }
}
