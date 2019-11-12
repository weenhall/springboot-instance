package com.example.springbootactiviti.base;

import org.activiti.engine.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by ween on 2019/3/22
 */
@Component
public class ActivitiCoreBase {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private HistoryService historyService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public IdentityService getIdentityService() {
        return identityService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }
}
