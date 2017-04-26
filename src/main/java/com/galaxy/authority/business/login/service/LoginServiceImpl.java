package com.galaxy.authority.business.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.dao.login.ILoginDao;

@Repository
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginDao dao;
	
	/**
	 * 用户登录
	 */
	@Override
	public boolean userLogin(Map<String, Object> paramMap) {
		//dao
		int count =dao.getUserLoginInfo(paramMap);
		return count>0;
	}


}
