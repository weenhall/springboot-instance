package com.example.springbootactiviti;

import com.example.springbootactiviti.base.ActivitiCoreBase;
import com.example.springbootactiviti.util.CustomProcessDiagramGenerator;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例相关
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessInstanceTest {

	@Autowired
	ActivitiCoreBase coreBase;
	@Resource
	ProcessEngineConfiguration processEngineConfiguration;

	/**
	 * 获取所有流程实例
	 */
	@Test
	public void getAllUserProcessInstance() {
		List<ProcessInstance> listOfPI = coreBase.getRuntimeService().createProcessInstanceQuery().list();
		listOfPI.forEach(pi -> {
			log.info("============分割线=============");
			log.info("流程实例ID:{}", pi.getId());
			log.info("流程名称:{}", pi.getProcessDefinitionName());
			log.info("业务标题:{}", pi.getName());
			Task task = coreBase.getTaskService().createTaskQuery().processInstanceId(pi.getId()).singleResult();
			log.info("当前任务:{}", task.getName());
			log.info("办理人:{}", task.getAssignee());
			log.info("实例开始时间:{}", pi.getStartTime());
			log.info("实例开始用户:{}", pi.getStartUserId());
			log.info("状态:{}", (pi.isSuspended() ? "已挂起" : "运行中"));
		});
	}

	/**
	 * 删除某个流程实例
	 */
	@Test
	public void removeProcessInstance() {
		String processInstanceId = "";
		coreBase.getRuntimeService().deleteProcessInstance(processInstanceId,"不想申请了");
	}

	/**
	 * 获取某个流程实例的流转情况图
	 */
	@Test
	public void getProcessInstanceImage() {
		String processInstanceId = "95001";
		try {
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = coreBase.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			// 获取流程中已经执行的节点，按照执行先后顺序排序
			List<HistoricActivityInstance> historicActivityInstances = coreBase.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
					.orderByHistoricActivityInstanceId().asc().list();
			// 高亮已经执行流程节点ID集合
			List<String> highLightedActivitiIds = new ArrayList<>();
			for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
				highLightedActivitiIds.add(historicActivityInstance.getActivityId());
			}

			List<HistoricProcessInstance> historicFinishedProcessInstances = coreBase.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).finished().list();
			ProcessDiagramGenerator processDiagramGenerator = null;
			// 如果还没完成，流程图高亮颜色为绿色，如果已经完成为红色
			if (!CollectionUtils.isEmpty(historicFinishedProcessInstances)) {
				// 如果不为空，说明已经完成
				processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
			} else {
				processDiagramGenerator = new CustomProcessDiagramGenerator();
			}
			BpmnModel bpmnModel = coreBase.getRepositoryService().getBpmnModel(historicProcessInstance.getProcessDefinitionId());
			// 高亮流程已发生流转的线id集合
			List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);
			// 使用默认配置获得流程图表生成器，并生成追踪图片字符流
			InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds, "宋体", "微软雅黑", "黑体", null, 2.0);
			File file = new File("target/userProcess.png");
			FileUtils.copyInputStreamToFile(imageStream, file);
		} catch (Exception e) {
			System.out.println("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage());
			e.printStackTrace();
		}
	}

	protected static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
		// 高亮流程已发生流转的线id集合
		List<String> highLightedFlowIds = new ArrayList<>();
		// 全部活动节点
		List<FlowNode> historicActivityNodes = new ArrayList<>();
		// 已完成的历史活动节点
		List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

		for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
			FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
			historicActivityNodes.add(flowNode);
			if (historicActivityInstance.getEndTime() != null) {
				finishedActivityInstances.add(historicActivityInstance);
			}
		}

		FlowNode currentFlowNode = null;
		FlowNode targetFlowNode = null;
		// 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
		for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
			// 获得当前活动对应的节点信息及outgoingFlows信息
			currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
			List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

			/**
			 * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
			 */
			if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
				// 遍历历史活动节点，找到匹配流程目标节点的
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
					if (historicActivityNodes.contains(targetFlowNode)) {
						highLightedFlowIds.add(targetFlowNode.getId());
					}
				}
			} else {
				List<Map<String, Object>> tempMapList = new ArrayList<>();
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
						if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
							Map<String, Object> map = new HashMap<>();
							map.put("highLightedFlowId", sequenceFlow.getId());
							map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
							tempMapList.add(map);
						}
					}
				}

				if (!CollectionUtils.isEmpty(tempMapList)) {
					// 遍历匹配的集合，取得开始时间最早的一个
					long earliestStamp = 0L;
					String highLightedFlowId = null;
					for (Map<String, Object> map : tempMapList) {
						long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
						if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
							highLightedFlowId = map.get("highLightedFlowId").toString();
							earliestStamp = highLightedFlowStartTime;
						}
					}

					highLightedFlowIds.add(highLightedFlowId);
				}

			}

		}
		return highLightedFlowIds;
	}
}
