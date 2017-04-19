package com.galaxy.authority.business.user.service;

import java.util.Map;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.user.UserBean;

public interface IUserService {
	boolean saveUser(Map<String,Object> map);
	boolean updateUser(Map<String,Object> paramMap);
	Page<Map<String, Object>> getUserList(Map<String,Object> paramMap);
	
	boolean outtageUser(Map<String,Object> paramMap);
	boolean deleteUser(Map<String,Object> paramMap);
	boolean editUser(UserBean bean);
	boolean resetPassword(Map<String,Object> paramMap);
}
