package com.galaxy.authority.dao.user;

import com.galaxy.authority.bean.user.TestUserBean;

public interface ITestUserMapper {
	TestUserBean getFileByid(int id);
	void saveUser(TestUserBean bean);
}
