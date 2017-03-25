package com.galaxy.authority.user.service;

import com.galaxy.authority.bean.user.TestUserBean;

public interface ITestUserService {
	TestUserBean getUser(int id);
	void saveUser(TestUserBean userBean);
}
