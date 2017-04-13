package com.galaxy.authority.business.role.service;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RoleBean;
import com.galaxy.authority.bean.user.UserBean;

public interface IRoleService {
	/**
	 * 获取角色显示列表
	 * @param paramMap
	 * @return
	 */
	Page<UserBean> getRoleList(Map<String, Object> paramMap);
	/***
	 * 新增角色
	 * @param bean
	 * @return
	 */
	boolean saveRole(RoleBean bean);
	/**
	 * 删除角色
	 * @param map
	 * @return
	 */
	int delRoleById(Map<String, Object> paramMap);
	/**
	 * 通过id获取角色bean
	 * @param id
	 * @return
	 */
	RoleBean getRoleById(long id);
	/**
	 * 更新角色
	 * @param bean
	 * @return
	 */
	boolean updateRole(RoleBean bean);
	/**
	 * 通过部门id获取部门人数list
	 * @param paramString
	 * @return
	 */
	List<Map<String, Object>> getUserListByDeptId(Map<String, Object> paramMap);

}
