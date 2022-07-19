package com.example.springbootactiviti.engine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;

public class EngineTest {

	@Test
	public void init(){
		//创建引擎
		ProcessEngineConfiguration configuration=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		String driverName="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/flow-demo?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&characterSetResults=utf8&useSSL=false&verifyServerCertificate=false&autoReconnct=true&autoReconnectForPools=true&allowMultiQueries=true&nullCatalogMeansCurrent=true";
		String username="root";
		String password="1234";
		DataSource dataSource=new PooledDataSource(driverName,url,username,password);
		configuration.setDataSource(dataSource);
		configuration.setProcessEngineName("手动创建引擎");
		configuration.setHistoryLevel(HistoryLevel.FULL);
		//是否使用默认用户体系
		configuration.setDbIdentityUsed(false);
		//是否更新数据库
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
		ProcessEngine engine=configuration.buildProcessEngine();
		System.out.println(engine.getName());

		//启动流程实例
		ProcessInstance instance=engine.getRuntimeService()
				.startProcessInstanceByKeyAndTenantId("grouptask","TEST");
		System.out.println(instance.getProcessDefinitionName()+"启动");
	}
}
