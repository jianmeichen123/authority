package com.galaxy.authority.business.user.service;

import java.util.Map;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.user.UserBean;

public interface IUserService {
	boolean saveUser(Map<String,Object> map);
	Page<UserBean> getUserList(Map<String,Object> paramMap);
}
