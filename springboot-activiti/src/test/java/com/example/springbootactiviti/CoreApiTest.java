package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

/**
 * @author ween
 */
@Slf4j
@SpringBootTest
public class CoreApiTest {
    @Autowired
    ActivitiCoreBase activitiCoreBase;

    /**
     * 添加用户
     */
    @Test
    public void addUser(){
        User user=activitiCoreBase.getIdentityService().newUser("niko");
        user.setFirstName("ni");
        user.setLastName("ko");
        user.setEmail("niko@freelance.com");
        user.setPassword("1234");
        activitiCoreBase.getIdentityService().saveUser(user);
    }

    @Test
    public void queryUser() throws JsonProcessingException {
        String userId="niko";
        User user=activitiCoreBase.getIdentityService().createUserQuery()
                .userId(userId)
                .singleResult();
        ObjectMapper mapper=new ObjectMapper();
        log.info(mapper.writeValueAsString(user));
    }

    @Test
    public void deleteUser(){
        activitiCoreBase.getIdentityService().deleteUser("niko");
    }

    /**
     * HistoryService 测试
     */
    @Test
    public void HistoryProcessInstance() {
        List<HistoricProcessInstance> list = activitiCoreBase.getHistoryService().createHistoricProcessInstanceQuery()
                .finished().list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            log.info("流程实例id: {}",historicProcessInstance.getId());
            log.info("部署id: {}",historicProcessInstance.getDeploymentId());
            log.info("结束event: {}",historicProcessInstance.getEndActivityId());
            log.info("流程名称: {}",historicProcessInstance.getName());
            log.info("PROC_DEF_ID: {}",historicProcessInstance.getProcessDefinitionId());
            log.info("流程定义Key: {}",historicProcessInstance.getProcessDefinitionKey());
            log.info("流程定义名称: {}",historicProcessInstance.getProcessDefinitionName());
            log.info("开始event: {}",historicProcessInstance.getStartActivityId());
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
            log.info("id:{}",pd.getId());
            log.info("name:{}",pd.getName());
            log.info("key:{}",pd.getKey());
            log.info("是否挂起:{}",pd.isSuspended());
            log.info("version:{}",pd.getVersion());
            log.info("=====================");
        });
    }

    /**
     *  启动流程实例
     */
    @Test
    public void processInstanceStart(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().startProcessInstanceByKey(processDefiniteKey);
        log.info("流程实例ID:{}",instance.getId());//流程实例ID
        log.info("流程定义ID:{}",instance.getProcessDefinitionId());//流程定义ID
    }

    //查询流程实例
    @Test
    public void queryProcessInstance(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().createProcessInstanceQuery()
                .processDefinitionKey(processDefiniteKey)
                .singleResult();
       log.info("流程实例ID:{}",instance.getId());
       log.info("流程定义ID:{}",instance.getProcessDefinitionId());
    }

    /**
     *删除流程实例
     */
    @Test
    public void deleteProcessInstance(){
        String processDefiniteKey="leaveProcess";
        ProcessInstance instance=activitiCoreBase.getRuntimeService().createProcessInstanceQuery()
                .processDefinitionKey(processDefiniteKey)
                .singleResult();
        String processInstanceId=instance.getProcessInstanceId();
        activitiCoreBase.getRuntimeService().deleteProcessInstance(processInstanceId,"删除测试");
    }
}
