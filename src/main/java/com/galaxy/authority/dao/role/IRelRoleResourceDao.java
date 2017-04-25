package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RelRoleResource;

public interface IRelRoleResourceDao {

	//批量保存角色资源信息
	boolean saveRelRoleResource(List<RelRoleResource> listBean);
	//通过角色id，资源id，获取角色资源关联id
	String getRelRoleResId(Map<String, Object> paramMap);
	//删除角色资源关联信息
	int delRelRoleResource(Map<String, Object> scopemap);

}
