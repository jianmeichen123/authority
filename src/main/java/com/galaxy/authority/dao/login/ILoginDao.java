package com.galaxy.authority.dao.login;

import java.util.Map;

public interface ILoginDao {
	
	//获取登录信息
	int getUserLoginInfo(Map<String, Object> paramMap);


}
