package com.ween.springbootflowable.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	private static final String FONT_DEFAULT_NAME="宋体";

	@Override
	public void configure(SpringProcessEngineConfiguration configuration) {
		configuration.setActivityFontName(FONT_DEFAULT_NAME);
		configuration.setLabelFontName(FONT_DEFAULT_NAME);
		configuration.setAnnotationFontName(FONT_DEFAULT_NAME);
		//for test env
//		configuration.setJdbcUrl("jdbc:h2:mem:flowable");
//		configuration.setDatabaseSchemaUpdate("create-drop");
	}
}
