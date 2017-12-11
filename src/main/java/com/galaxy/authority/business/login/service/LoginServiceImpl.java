package com.galaxy.authority.business.login.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.dao.depart.IDepartDao;
import com.galaxy.authority.dao.login.ILoginDao;
import com.galaxy.authority.dao.role.IRoleDao;

@Repository
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginDao dao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IDepartDao deptDao;
	
	/**
	 * 用户登录
	 */
	@Override
	public Map<String,Object> userLogin(Map<String, Object> paramMap) {
		Map<String,Object> userInfo = dao.getUserLoginInfo(paramMap);
		if(userInfo != null && userInfo.containsKey("id"))
		{
			Map<String, Object> query = new HashMap<>();
			query.put("userId", userInfo.get("id"));
			query.put("companyId", userInfo.get("companyId"));
			query.put("productType", paramMap.get("productType"));
			List<String> roleCodes = roleDao.selectRoleCodeByUserId(query);
			//设置角色信息
			if(roleCodes != null && roleCodes.size() >0)
			{
				userInfo.put("roleId", roleCodes.iterator().next());
				userInfo.put("roleIds", roleCodes);
			}
			
			//设置部门信息
			List<Map<String,Object>> departs = deptDao.selectUserDep(query);
			if(departs != null && departs.size() >0)
			{
				Map<String,Object> depart = departs.iterator().next();
				userInfo.put("departmentId", depart.get("departmentId"));
				userInfo.put("departmentName", depart.get("departmentName"));
			}
			//资源权限
			List<Map<String, Object>> res = userService.getUserResources(query);
			if(res != null)
			{
				//数据权限
				Map<String,Set<Integer>> userIdsMap = userService.getUserResourceScope(query);
				Set<Integer> defaultScope = new HashSet<>(1);
				defaultScope.add(0);
				for(Map<String, Object> item : res)
				{
					String key = item.get("resourceMark")+"";
					if(userIdsMap.containsKey(key))
					{
						item.put("userIds", userIdsMap.get(key));
					}
					else
					{
						item.put("userIds", defaultScope);
					}
				}
			}
			userInfo.put("allResourceToUser", res);
		}
		return userInfo;
	}

	/**
	 * APP用户登录
	 */
	@Override
	public Map<String,Object> userLoginForApp(Map<String, Object> paramMap) {
		Map<String,Object> userInfo = dao.getUserLoginInfoForApp(paramMap);
		if(userInfo != null && userInfo.containsKey("id"))
		{
			Map<String, Object> query = new HashMap<>();
			query.put("userId", userInfo.get("id"));
			query.put("companyId", userInfo.get("companyId"));
			query.put("productType", paramMap.get("productType"));
			List<String> roleCodes = roleDao.selectRoleCodeByUserId(query);
			//设置角色信息
			if(roleCodes != null && roleCodes.size() >0)
			{
				userInfo.put("roleId", roleCodes.iterator().next());
				userInfo.put("roleIds", roleCodes);
			}
			
			//设置部门信息
			List<Map<String,Object>> departs = deptDao.selectUserDep(query);
			if(departs != null && departs.size() >0)
			{
				Map<String,Object> depart = departs.iterator().next();
				userInfo.put("departmentId", depart.get("departmentId"));
				userInfo.put("departmentName", depart.get("departmentName"));
			}
			//资源权限
			List<Map<String, Object>> res = userService.getUserResources(query);
			if(res != null)
			{
				//数据权限
				Map<String,Set<Integer>> userIdsMap = userService.getUserResourceScope(query);
				Set<Integer> defaultScope = new HashSet<>(1);
				defaultScope.add(0);
				for(Map<String, Object> item : res)
				{
					String key = item.get("resourceMark")+"";
					if(userIdsMap.containsKey(key))
					{
						item.put("userIds", userIdsMap.get(key));
					}
					else
					{
						item.put("userIds", defaultScope);
					}
				}
			}
			userInfo.put("allResourceToUser", res);
		}
		return userInfo;
	}

	@Override
	public Map<String, Object> loginSelf(Map<String, Object> paramMap) {
		return dao.loginSelf(paramMap);
	}


	

}
