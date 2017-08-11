package com.galaxy.authority.business.role.service;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RelRoleResource;
import com.galaxy.authority.bean.role.RelRoleScope;
import com.galaxy.authority.bean.role.RelRoleUser;
import com.galaxy.authority.bean.role.RelSpUsers;
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
	/**
	 * 保存角色资源关联信息
	 * @param listBean
	 * @return
	 */
	boolean saveRelRoleResource(List<RelRoleResource> listBean);
	/**
	 * 获取角色资源关联id
	 * @param roleId
	 * @param resId
	 * @return
	 */
	String getRelRoleResId(Map<String, Object> paramMap);
	/**
	 * 保存角色资源关联数据范围
	 * @param spListBean
	 * @return
	 */
	Boolean saveRelRoleScope(List<RelRoleScope> spListBean);
	/**
	 * 删除角色资源与数据范围关联表信息
	 * @param scopemap
	 */
	int delRelRoleScope(Map<String, Object> scopemap);
	/**
	 * 删除角色资源关联信息
	 * @param scopemap
	 * @return
	 */
	int delRelRoleResource(Map<String, Object> scopemap);
	/**
	 * 获取当前部门以及子部门id和name
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getDeptIdName(Map<String, Object> paramMap);
	/**
	 * 获取角色资源与范围关联id
	 * @param paramMap
	 * @return
	 */
	String getRelRoleScopeId(Map<String, Object> paramMap);
	/**
	 * 保存角色资源与数据范围，用户的关联信息
	 * @param suListBean
	 * @return
	 */
	Boolean saveRelSpUsers(List<RelSpUsers> suListBean);
	/**
	 * 删除角色资源与数据范围，用户关联信息
	 * @param sm
	 */
	int delRelSpUsers(Map<String, Object> sm);
	/**
	 * 通过资源id获取关联表信息（用户回显）
	 */
	List<Map<String, Object>> isDisplayByResId(Map<String, Object> mp);
	/**
	 * 获取对应的用户name或部门name
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> showUernameOrDeptName(Map<String, Object> paramMap);
	/**
	 * 获取资源列表
	 */
	List<Map<String, Object>> getResList(Map<String, Object> paramMap);
	/**
	 * 保存权限设置
	 * @param paramMap
	 * @return
	 */
	boolean saveResource(Map<String, Object> paramMap);
	/**
	 * 获取用户的角色ID
	 * @param paramMap
	 * @return
	 */
	List<Long> selectRoleIdByUserId(Map<String, Object> paramMap);
	/**
	 * 是否存在改角色名称
	 * @param roleName
	 * @return
	 */
	int isExitRole(String roleName);
	/**
	 * 获取角色关联账号信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> showUerName(Map<String, Object> paramMap);
	/**
	 * 已选用户判断是否已经绑定角色
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> isRelRoleUser(Map<String, Object> paramMap);
	/**
	 * 获取用户的角色code
	 * @param paramMap
	 * @return
	 */
	List<String> selectRoleCodeByUserId(Map<String, Object> paramMap);
	/**
	 * 获取角色code
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> getRoleCodeByUserId(Map<String, Object> query);
}
