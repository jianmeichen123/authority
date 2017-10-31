package com.galaxy.authority.dao.user;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.user.UserBean;

public interface IUserDao {
	int saveUser(UserBean userBean);
	int updateUser(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserList(Map<String,Object> paramMap);
	int getUserListCount(Map<String,Object> paramMap);
	
	int outtageUser(Map<String,Object> paramMap);
	int deleteUser(Map<String,Object> paramMap);
	int editUser(UserBean bean);
	
	int resetPassword(Map<String,Object> paramMap);
	
	List<Map<String,Object>> findUserByNameTdj(Map<String,Object> paramMap);
	Map<String,Object> getUserById(Map<String,Object> paramMap);
	List<Map<String,Object>> getUsersByDepId(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserByIds(Map<String,Object> paramMap);
	List<Map<String,Object>> getUsersByKey(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserResources(Map<String,Object> paramMap);
	List<Map<String,Object>> getUserScope(Map<String,Object> paramMap);
	List<Integer> geDepUserIds(Integer userId);
	//通过userid获取用户名称
	List<Map<String, Object>> getUserNameList(Map<String, Object> map);
	//根据用户id获取用户名和所在部门
	List<Map<String, Object>> getCreadIdInfo(Map<String, Object> map);
	//判断登录账号是否存在
	int isExitUser(String loginName);
	
	void updatePwd(Map<String, Object> map);
	
	List<Map<String,Object>> getResources(Map<String,Object> paramMap);
	List<Integer> getUserIdList();
	int checkBindRole(Map<String, Object> paramMap);
}
