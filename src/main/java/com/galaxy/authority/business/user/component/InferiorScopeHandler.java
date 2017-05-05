package com.galaxy.authority.business.user.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.authority.dao.depart.IDepartDao;
import com.galaxy.authority.dao.user.IUserDao;
/**
 * 下级部门所有人
 * @author wangsong
 *
 */
@Component
public class InferiorScopeHandler extends AbstractScopeHandler implements ScopeHandler {

	@Autowired
	IUserDao userDao;
	@Autowired
	IDepartDao depDao;
	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 5 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
	
		List<Integer> userIds = null;
		Integer userId = getVal(data,"userId");
		//查询用户
		Map<String,Object> query = new HashMap<>();
		query.put("userId", userId);
		Map<String,Object> user = userDao.getUserById(query);
		
		//查询所有下级部门ID
		Integer depId = getVal(user,"depId");
		List<Integer> depIds = new ArrayList<>();
		getChildrenDepIds(Arrays.asList(depId), depIds);
		
		//查询下级部门用户
		if(depIds != null && depIds.size() >0)
		{
			query = new HashMap<>();
			query.put("depIds", depIds);
			List<Map<String,Object>> users = userDao.getUsersByDepId(query);
			if(users != null && users.size() > 0)
			{
				userIds = new ArrayList<>(users.size());
				for(Map<String,Object> item : users)
				{
					Integer id = getVal(item,"userId");
					userIds.add(id);
				}
			}
		}
		
		return userIds;
	}
	
	private void getChildrenDepIds(List<Integer> parentIds, List<Integer> depIds)
	{
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("pids", parentIds);
		List<Integer> list = depDao.getChildrenIds(paramMap);
		if(list !=null && list.size() > 0)
		{
			depIds.addAll(list);
			getChildrenDepIds(list,depIds);
		}
	}

}
