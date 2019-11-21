package com.example.springbootactiviti;

import com.alibaba.fastjson.JSON;
import com.example.springbootactiviti.base.ActivitiCoreBase;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.UserTask;
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
    private ActivitiCoreBase coreBase;

    //准备数据
    @Test
    public void prepareTestData() {
        Deployment deployment = coreBase.getRepositoryService().createDeployment()
                .name("请假流程")
                .category("审批业务类")
                .key("a11")
                .addClasspathResource("processes/leave.bpmn")
                .deploy();
        System.out.println("部署ID>>" + deployment.getId());

//        Group group1 = coreBase.getIdentityService().newGroup("HR");
//        group1.setName("HR");
//        group1.setType("HRassignment");
//        coreBase.getIdentityService().saveGroup(group1);//建立HR组
//
//        Group group2 = coreBase.getIdentityService().newGroup("ZJ");
//        group2.setName("ZJ");
//        group2.setType("ZJassignment");
//        coreBase.getIdentityService().saveGroup(group2);//建立ZJ组
//
//        Group group3 = coreBase.getIdentityService().newGroup("EP");
//        group3.setName("EP");
//        group3.setType("EPassignment");
//        coreBase.getIdentityService().saveGroup(group3);//建立员工组
//
//         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("HR1"));//高管
//         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("HR2"));//高管
//         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("ZJ"));//总监
//         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("ZJ2"));//总监
//         coreBase.getIdentityService().saveUser( coreBase.getIdentityService().newUser("wuwh"));//员工
//
//         coreBase.getIdentityService().createMembership("HR1", "HR");
//         coreBase.getIdentityService().createMembership("HR2", "HR");
//         coreBase.getIdentityService().createMembership("ZJ", "ZJ");
//         coreBase.getIdentityService().createMembership("ZJ2", "ZJ");
//         coreBase.getIdentityService().createMembership("wuwh", "EP");
    }

    //打开一个实例
    @Test
    public void startProcessInstance() {
        //指定下一步用户
        String userId = "wuwh";
        Map<String, Object> val = new HashMap<>();
        val.put("next", userId);
//        val.put("arg0","arg0");
//        val.put("arg1","arg1");
        //流程实例的key值
        String processDefinitionKey = "process_1573537843438";

        ProcessInstance instance = coreBase.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, val);

        System.out.println("=========启动一个流程实例==========");
        System.out.println("流程定义的Id:" + instance.getProcessDefinitionId());
        System.out.println("流程定义的Name:" + instance.getProcessDefinitionName());
        System.out.println("流程定义的Key:" + instance.getProcessDefinitionKey());
        System.out.println("流程定义的Version:" + instance.getProcessDefinitionVersion());
        System.out.println("流程定义的Id:" + instance.getProcessDefinitionId());

        System.out.println("流程部署的Id:" + instance.getDeploymentId());
        System.out.println("流程的BusinessKey:"+instance.getBusinessKey());
        System.out.println("流程是否挂起:"+instance.isSuspended());
        System.out.println("流程携带的变量:"+instance.getProcessVariables().toString());
        System.out.println("流程租户Id:"+instance.getTenantId());
        System.out.println("流程名称:"+instance.getName());
        System.out.println("流程描述:"+instance.getDescription());
        System.out.println("流程LocalizedName:"+instance.getLocalizedName());
        System.out.println("流程LocalizedDescription:"+instance.getLocalizedDescription());
        System.out.println("流程启动时间:"+instance.getStartTime());
        System.out.println("流程启动用户:"+instance.getStartUserId());
    }

    //查询任务
    @Test
    public void queryTask() {
        String assignee = "wuwh2";
        List<Task> list = coreBase.getTaskService().createTaskQuery()
                .taskAssignee(assignee)
                .list();
        System.out.println("查询到wuwh2下有以下任务");
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("==========分割线==========");
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务描述:" + task.getDescription());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("本次任务变量:"+task.getTaskLocalVariables());
                System.out.println("流程定义的变量"+task.getProcessVariables());
//                String des = task.getDescription();
//                Map<String, Object> map = JSON.parseObject(des, Map.class);
//                System.out.println(map.toString());
            }
        }
    }

    @Test
    public void claimTask(){
        String claimUser="zhangshan";
        coreBase.getTaskService().claim("7504",claimUser);
    }
    //办理任务
    @Test
    public void completeTask() {
        System.out.println("办理某条任务");
        String taskId = "20003";
        Map<String, Object> val = new HashMap<>();
        val.put("next","wuwh3");
//        val.put("leave_days", 2);
        System.out.println("获取当前步骤任务变量");
        Map<String, Object> map = coreBase.getTaskService().getVariables(taskId);
        for (String key : map.keySet()) {
            System.out.println(key + ">>" + map.get(key));
        }
        coreBase.getTaskService().complete(taskId,val);
    }

    @Test
    public void getVariables() {
        Map<String, Object> variablesMap = coreBase.getRuntimeService().getVariablesLocal("2504");
        System.out.println(variablesMap.toString());
    }


}
