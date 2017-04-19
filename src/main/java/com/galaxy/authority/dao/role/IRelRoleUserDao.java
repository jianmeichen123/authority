package com.galaxy.authority.dao.role;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.role.RelRoleUser;

public interface IRelRoleUserDao {
	
	//获取用户角色关联记录数
	public int getRelRoleUserListCount(Map<String, Object> paramMap);
	//批量保存用户角色关联信息
	public int saveRelRoleUserBatch(List<RelRoleUser> listBean);
	//获取用户角色关联信息
	public RelRoleUser getRelRoleUser(Map<String, Object> paramMap);
	//更新用户角色关联信息
	public int updateRelRoleUser(RelRoleUser relbean);
	//用户与角色解除绑定
	public int delRelRoleUer(Map<String, Object> map);

}
