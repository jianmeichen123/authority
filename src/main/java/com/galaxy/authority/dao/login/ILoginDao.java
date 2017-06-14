package com.galaxy.authority.dao.login;

import java.util.Map;

public interface ILoginDao {
	
	//获取登录信息
	Map<String,Object> getUserLoginInfo(Map<String, Object> paramMap);

	//获取登录信息
	Map<String,Object> getUserLoginInfoForApp(Map<String, Object> paramMap);
	
	//权限系统内部登录
	Map<String,Object> loginSelf(Map<String,Object> paramMap);
	
}
