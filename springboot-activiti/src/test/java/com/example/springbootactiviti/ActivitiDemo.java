package com.example.springbootactiviti;

import com.alibaba.fastjson.JSON;
import com.example.springbootactiviti.base.ActivitiCoreBase;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ween on 2019/3/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDemo {

    @Resource
    ActivitiCoreBase coreBase;

    //准备数据
    @Test
    public void prepareTestData(){
        Deployment deployment=coreBase.getRepositoryService().createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")
                .deploy();
        System.out.println("部署ID>>"+deployment.getId());

        Group group1 = coreBase.getIdentityService().newGroup("HR");
        group1.setName("HR");
        group1.setType("HRassignment");
        coreBase.getIdentityService().saveGroup(group1);//建立HR组

        Group group2 = coreBase.getIdentityService().newGroup("ZJ");
        group2.setName("ZJ");
        group2.setType("ZJassignment");
        coreBase.getIdentityService().saveGroup(group2);//建立ZJ组

        Group group3 = coreBase.getIdentityService().newGroup("EP");
        group3.setName("EP");
        group3.setType("EPassignment");
        coreBase.getIdentityService().saveGroup(group3);//建立员工组

         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("HR1"));//高管
         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("HR2"));//高管
         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("ZJ"));//总监
         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("ZJ2"));//总监
         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("wuwh"));//员工

         coreBase.getIdentityService().createMembership("HR1", "HR");
         coreBase.getIdentityService().createMembership("HR2", "HR");
         coreBase.getIdentityService().createMembership("ZJ", "ZJ");
         coreBase.getIdentityService().createMembership("ZJ2", "ZJ");
         coreBase.getIdentityService().createMembership("wuwh", "EP");
    }

    //打开一个实例
    @Test
    public void startProcessInstance(){
        String userId="wuwh";//下一步骤用户ID
        String processDefinitionKey="leaveProcess";//流程实例的key值
        Map<String,Object> val=new HashMap<>();
        val.put("userKey",userId);

        ProcessInstance instance=coreBase.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey,val);

    }

    //查询任务
    @Test
    public void queryTask(){
        String assignee="wuwh";
        List<Task> list=coreBase.getTaskService().createTaskQuery()
                .taskAssignee(assignee)
                .list();
        if(list!=null&&list.size()>0){
            for(Task task:list){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
            }
        }
    }

    //办理任务
    @Test
    public void completeTask(){
        String taskId="20007";
        Map<String,Object> val=new HashMap<>();
        val.put("days",2);
        coreBase.getTaskService().complete(taskId,val);
    }


}
