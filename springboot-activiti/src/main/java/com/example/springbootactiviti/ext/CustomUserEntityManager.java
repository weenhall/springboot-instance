package com.example.springbootactiviti.ext;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;

import java.util.List;
import java.util.Map;

/**
 * 自定义用户管理器
 */
public class CustomUserEntityManager implements UserDataManager {

	public CustomUserEntityManager(){

	}
	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl userQuery, Page page) {
		return null;
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl userQuery) {
		return 0;
	}

	@Override
	public List<Group> findGroupsByUser(String s) {
		return null;
	}

	@Override
	public List<User> findUsersByNativeQuery(Map<String, Object> map, int i, int i1) {
		return null;
	}

	@Override
	public long findUserCountByNativeQuery(Map<String, Object> map) {
		return 0;
	}

	@Override
	public UserEntity create() {
		return null;
	}

	@Override
	public UserEntity findById(String s) {
		return null;
	}

	@Override
	public void insert(UserEntity entity) {

	}

	@Override
	public UserEntity update(UserEntity entity) {
		return null;
	}

	@Override
	public void delete(String s) {

	}

	@Override
	public void delete(UserEntity entity) {

	}
}
