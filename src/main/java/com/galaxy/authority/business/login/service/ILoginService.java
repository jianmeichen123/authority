package com.galaxy.authority.business.login.service;

import java.util.Map;

public interface ILoginService {

	/**
	 * 登录
	 * @param paramMap
	 * @return
	 */
	Map<String,Object> userLogin(Map<String, Object> paramMap);
	
	/**
	 * APP登录
	 * @param paramMap
	 * @return
	 */
	Map<String,Object> userLoginForApp(Map<String, Object> paramMap);

}
