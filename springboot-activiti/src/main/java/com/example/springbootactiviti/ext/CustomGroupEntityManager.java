package com.example.springbootactiviti.ext;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义用户组管理
 */
public class CustomGroupEntityManager extends MybatisGroupDataManager {

	private final static String GROUP_DEFAULT="ANONYMOUS";
	private final static String GROUP_HR="HR";
	private final static String GROUP_MANAGER="MANAGER";
	private static final Map<String,String> localGroup;

	static {
		localGroup=new HashMap<>();
		localGroup.put("Alice",GROUP_HR);
		localGroup.put("Bob",GROUP_HR);

		localGroup.put("Pat",GROUP_MANAGER);
		localGroup.put("Peggy",GROUP_MANAGER);
	}

	public CustomGroupEntityManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
		super(processEngineConfiguration);
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		return getDbSqlSession().selectList("selectGroupByQueryCriteria",query, page);
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		List<Group> userGroups=new ArrayList<>();
		if(localGroup.get(userId)!=null){
			GroupEntity entity=new GroupEntityImpl();
			entity.setName(localGroup.get(userId));
			entity.setType(localGroup.get(userId));
			entity.setId(localGroup.get(userId));
			userGroups.add(entity);
		}else {
			GroupEntity entity=new GroupEntityImpl();
			entity.setName(GROUP_DEFAULT);
			entity.setType(GROUP_DEFAULT);
			entity.setId(GROUP_DEFAULT);
			userGroups.add(entity);
		}
		return userGroups;
	}
}
