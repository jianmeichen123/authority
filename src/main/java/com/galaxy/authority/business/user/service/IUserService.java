package com.galaxy.authority.business.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.user.UserBean;

public interface IUserService {
	boolean saveUser(Map<String,Object> map);
	boolean updateUser(Map<String,Object> paramMap);
	Page<Map<String, Object>> getUserList(Map<String,Object> paramMap);
	
	boolean outtageUser(Map<String,Object> paramMap);
	boolean deleteUser(Map<String,Object> paramMap);
	boolean editUser(UserBean bean);
	ResultBean resetPassword(Map<String,Object> paramMap);
	
	List<Map<String,Object>> findUserByName(Map<String,Object> paramMap);
	Map<String,Object> getUserById(Map<String,Object> paramMap);
	List<Map<String,Object>> getUsersByDepId(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserByIds(Map<String,Object> paramMap);
	List<Map<String,Object>> getUsersByKey(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserResources(Map<String,Object> paramMap);
	public List<Map<String,Object>> getUserScope(Map<String,Object> paramMap);
	public Map<String,Set<Integer>> getUserResourceScope(Map<String,Object> paramMap);
	//获取共享用户list
	List<Map<String, Object>> getShareUserList(Map<String, Object> map);
	//根据用户id获取用户名和所在部门
	List<Map<String, Object>> getCreadIdInfo(Map<String, Object> map);
	//判断登录账号是否存在
	int isExitUser(String loginName);
	
	int updatePwd(Map<String, Object> map);
	
	public List<Map<String,Object>> getResources(Map<String,Object> params);
	//app 修改密码
	ResultBean resetPasswordForApp(Map<String, Object> map);
	//检测用户是否绑定角色
	boolean checkBindRole(Map<String, Object> paramMap);
}
