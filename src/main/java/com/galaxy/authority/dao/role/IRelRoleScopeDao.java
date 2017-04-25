package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RelRoleScope;

public interface IRelRoleScopeDao {

	//批量保存角色资源关联数据范围信息
	Boolean saveRelRoleScope(List<RelRoleScope> spListBean);
	//删除角色资源与数据范围关联表信息，通过角色资源id
	int delRelRoleScope(Map<String, Object> scopemap);
	//获取角色资源与范围关联id
	String getRelRoleScopeId(Map<String, Object> paramMap);

}
