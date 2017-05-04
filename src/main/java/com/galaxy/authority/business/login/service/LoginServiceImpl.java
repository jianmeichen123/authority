package com.galaxy.authority.business.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.dao.login.ILoginDao;
import com.galaxy.authority.dao.user.IUserDao;

@Repository
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginDao dao;
	@Autowired
	private IUserDao userDao;
	
	/**
	 * 用户登录
	 */
	@Override
	public Map<String,Object> userLogin(Map<String, Object> paramMap) {
		Map<String,Object> userInfo = dao.getUserLoginInfo(paramMap);
		if(userInfo != null && userInfo.containsKey("id"))
		{
			Map<String, Object> query = new HashMap<>();
			query.put("userId", userInfo.get("id"));
			query.put("companyId", userInfo.get("companyId"));
			List<Map<String, Object>> res = userDao.getUserResources(query);
			userInfo.put("allResourceToUser", res);
		}
		return userInfo;
	}


}
