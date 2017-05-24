package com.galaxy.authority.business.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.dao.login.ILoginDao;

@Repository
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginDao dao;
	@Autowired
	private IUserService userService;
	
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
			//资源权限
			List<Map<String, Object>> res = userService.getUserResources(query);
			if(res != null)
			{
				//数据权限
				List<Map<String, Object>> scopeList = userService.getUserScope(query);
				Map<String,Object> userIdsMap = new HashMap<>();
				for(Map<String, Object> item : scopeList)
				{
					if(item.containsKey("userIds"))
					{
						userIdsMap.put(item.get("resourceMark")+"", item.get("userIds"));
					}
				}
				for(Map<String, Object> item : res)
				{
					String key = item.get("resourceMark")+"";
					if(userIdsMap.containsKey(key))
					{
						item.put("userIds", userIdsMap.get(key));
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
			//资源权限
			List<Map<String, Object>> res = userService.getUserResources(query);
			if(res != null)
			{
				//数据权限
				List<Map<String, Object>> scopeList = userService.getUserScope(query);
				Map<String,Object> userIdsMap = new HashMap<>();
				for(Map<String, Object> item : scopeList)
				{
					if(item.containsKey("userIds"))
					{
						userIdsMap.put(item.get("resourceMark")+"", item.get("userIds"));
					}
				}
				for(Map<String, Object> item : res)
				{
					String key = item.get("resourceMark")+"";
					if(userIdsMap.containsKey(key))
					{
						item.put("userIds", userIdsMap.get(key));
					}
				}
			}
			userInfo.put("allResourceToUser", res);
		}
		return userInfo;
	}

}
