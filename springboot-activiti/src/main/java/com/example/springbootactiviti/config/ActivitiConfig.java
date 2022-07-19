package com.example.springbootactiviti.config;

import com.example.springbootactiviti.ext.CustomGroupEntityManager;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		//启用自定义用户组
		CustomGroupEntityManager manager=new CustomGroupEntityManager(processEngineConfiguration);
		processEngineConfiguration.setGroupDataManager(manager);
	}
}
