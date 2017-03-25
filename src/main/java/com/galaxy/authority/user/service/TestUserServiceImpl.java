package com.galaxy.authority.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.user.TestUserBean;
import com.galaxy.authority.dao.user.ITestUserMapper;

@Repository
public class TestUserServiceImpl implements ITestUserService{
	@Autowired
	private ITestUserMapper userMapper;
	
	@Override
	public TestUserBean getUser(int id){
		TestUserBean bean = userMapper.getFileByid(id);
		return bean;
	}
	
	@Override
	public void saveUser(TestUserBean userBean){
		//double tt = 100/0;
		userMapper.saveUser(userBean);
	}
	
}
