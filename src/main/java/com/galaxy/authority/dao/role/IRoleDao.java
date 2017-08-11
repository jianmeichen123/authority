package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RoleBean;

public interface IRoleDao {
	
	//获取角色列表
	public List<Map<String, Object>> getRoleList(Map<String, Object> paramMap);
	//获取角色列表总记录数
	public int getRoleListCount(Map<String, Object> paramMap);
	//保存角色
	public int saveRole(RoleBean bean);
	//删除角色
	public int delRoleById(Map<String, Object> paramMap);
	//通过id获取角色信息
	public RoleBean getRoleById(long id);
	//更新角色
	public boolean updateRole(RoleBean bean);
	//通过部门id获取部门人数list
	public List<Map<String, Object>> getChildNodesByDeptId(Map<String, Object> paramMap);
	//通过部门id获取部门下所有账号
	public List<Map<String, Object>> getUserNameByDeptId(Map<String, Object> deptParamMap);
	//通过角色id获取绑定账号信息list
	public List<Map<String, Object>> getBindUserInfoListById(Map<String, Object> paramMap);
	//获取角色绑定的关联账号
	public List<Map<String, Object>> getUserListByRoleId(Map<String, Object> userParamMap);
	//检测角色是否有绑定账号
	public int checkBindUser(Map<String, Object> paramMap);
	//获取资源树list
	public List<Map<String, Object>> getResourceTreeList(Map<String, Object> paramMap);
	//获取资源当前节点以及子节点信息list
	public List<Map<String, Object>> getResourceList(Map<String, Object> paramMap);
	//获取数据范围
	public List<Map<String, Object>> getDataScope(Map<String, Object> paramMap);
	//获取当前节点
	public List<Map<String, Object>> getcurrNodeByDeptId(Map<String, Object> paramMap);
	//通过资源id获取关联表信息（用户回显）
	public List<Map<String, Object>> isDisplayByResId(Map<String, Object> mp);
	//获取部门名称
	public List<Map<String, Object>> getDeptName(Map<String, Object> paramMap);
	//获取用户名称
	public List<Map<String, Object>> getUserName(Map<String, Object> paramMap);
	//获取用户的角色ID
	List<Long> selectRoleIdByUserId(Map<String, Object> paramMap);
	//是否存在角色名称
	public int isExitRole(String roleName);
	//获取角色关联账号信息
	public List<Map<String, Object>> showUerName(Map<String, Object> paramMap);
	//已选用户判断是否已经绑定角色
	public Map<String, Object> isRelRoleUser(Map<String, Object> paramMap);
	//获取用户的角色code
	List<String> selectRoleCodeByUserId(Map<String, Object> paramMap);
	//获取角色code
	public List<Map<String, Object>> getRoleCodeByUserId(Map<String, Object> query);
}
