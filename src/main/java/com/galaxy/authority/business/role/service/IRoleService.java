package com.galaxy.authority.business.role.service;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RelRoleUser;
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
	/**
	 * 通过角色id获取绑定账号信息list
	 * @param paramMap
	 * @return
	 */
	Page<RelRoleUser> getBindUserInfoListById(Map<String, Object> paramMap);
	/**
	 * 用户与角色解除绑定
	 * @param map
	 * @return
	 */
	int delRelRoleUer(Map<String, Object> map);
	/**
	 * 检测角色是否有绑定账号
	 * @param paramMap
	 * @return
	 */
	boolean checkBindUser(Map<String, Object> paramMap);
	/**
	 * 保存用户角色关联信息
	 * @param listBean
	 * @return
	 */
	boolean saveRelRoleUser(List<RelRoleUser> listBean);
	/**
	 * 获取用户角色关联信息
	 * @param paramMap
	 * @return
	 */
	RelRoleUser getRelRoleUser(Map<String, Object> paramMap);
	/**
	 * 更新用户角色关联信息
	 * @param relbean
	 */
	boolean updateRelRoleUser(RelRoleUser relbean);
	/**
	 * 获取资源树list
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getResourceTreeList(Map<String, Object> paramMap);
	/**
	 * 获取资源树的当前节点以及子节点信息list
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getResourceList(Map<String, Object> paramMap);
	/**
	 * 获取数据范围
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getDataScope(Map<String, Object> paramMap);

}
