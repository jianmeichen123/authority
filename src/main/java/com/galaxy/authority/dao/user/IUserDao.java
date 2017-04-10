package com.galaxy.authority.dao.user;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.user.UserBean;

public interface IUserDao {
	int saveUser(UserBean userBean);
	List<Map<String,Object>> getUserList(Map<String,Object> paramMap);
	int getUserListCount(Map<String,Object> paramMap);
}
