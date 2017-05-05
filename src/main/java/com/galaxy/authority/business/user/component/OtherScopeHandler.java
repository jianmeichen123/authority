package com.galaxy.authority.business.user.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.authority.dao.user.IUserDao;
/**
 * 其他(部门,用户)
 * @author wangsong
 *
 */
@Component
public class OtherScopeHandler extends AbstractScopeHandler implements ScopeHandler {

	@Autowired
	IUserDao userDao;
	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 6 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
		List<Integer> userIds = null;
		Integer isDep = getVal(data,"isDep");
		String otherIdStr = getVal(data,"otherId");
		if(otherIdStr != null && otherIdStr.length() >0)
		{
			userIds = new ArrayList<>();
			String[] ids = otherIdStr.split(",");
			if(isDep == 0)
			{
				List<Integer> depIds = new ArrayList<>();
				for(String id : ids)
				{
					depIds.add(Integer.valueOf(id));
				}
				Map<String,Object> query = new HashMap<>();
				query.put("depIds", depIds);
				List<Map<String,Object>> users = userDao.getUsersByDepId(query);
				if(users != null && users.size() > 0)
				{
					for(Map<String,Object> item : users)
					{
						Integer id = getVal(item,"userId");
						userIds.add(id);
					}
				}
			}
			else
			{
				for(String id : ids)
				{
					userIds.add(Integer.valueOf(id));
				}
			}
		}
		return userIds;
	}

}
