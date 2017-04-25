package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RelSpUsers;

public interface IRelSpUsersDao {
	
	//保存角色资源与数据范围，用户的关联信息
	Boolean saveRelSpUsers(List<RelSpUsers> suListBean);
	//删除角色资源与数据范围，用户关联信息
	int delRelSpUsers(Map<String, Object> sm);

}
