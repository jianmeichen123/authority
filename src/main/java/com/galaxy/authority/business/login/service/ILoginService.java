package com.galaxy.authority.business.login.service;

import java.util.Map;

public interface ILoginService {

	/**
	 * 登录
	 * @param paramMap
	 * @return
	 */
	boolean userLogin(Map<String, Object> paramMap);

}
