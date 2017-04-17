package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RelRoleUser;
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
	public List<Map<String, Object>> getUserListByDeptId(Map<String, Object> paramMap);
	//通过部门id获取部门下所有账号
	public List<Map<String, Object>> getUserNameByDeptId(Map<String, Object> deptParamMap);
	//通过角色id获取绑定账号信息list
	public List<Map<String, Object>> getBindUserInfoListById(Map<String, Object> paramMap);
	//获取角色绑定的关联账号
	public List<Map<String, Object>> getUserListByRoleId(Map<String, Object> userParamMap);
	//用户与角色解除绑定
	public int delRelRoleUer(Map<String, Object> map);
	//获取用户角色关联记录数
	public int getRelRoleUserListCount(Map<String, Object> paramMap);
	//检测角色是否有绑定账号
	public int checkBindUser(Map<String, Object> paramMap);
	//批量保存用户角色关联信息
	public int saveRelRoleUserBatch(List<RelRoleUser> listBean);
	//获取用户角色关联信息
	public RelRoleUser getRelRoleUser(Map<String, Object> paramMap);
	//更新用户角色关联信息
	public int updateRelRoleUser(RelRoleUser relbean);

}
