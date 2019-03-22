package com.example.springbootactiviti;

import com.alibaba.fastjson.JSON;
import com.example.springbootactiviti.base.ActivitiCoreBase;
import org.activiti.engine.IdentityService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
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
}
