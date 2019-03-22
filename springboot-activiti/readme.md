# 开始

## 指南
### 核心API
* RepositoryService : 管理流程定义部署
* RuntimeService : 管理流程实例
* TaskService : 管理流程任务
* IdentityService : 管理用户和用户组
* ManagementService : 提供对activiti中数据库的直接操作(一般不需要)
* HistoryServcie : 管理流程的历史数据
* FormService : 表单绑定
### 流程部署
> 文件类型
* bpmn
* png
* zip
> 关联的表
* act_re_deployment //保存流程部署定义
* act_ge_bytearray //保存流程部署文件
* act_re_procdef   



