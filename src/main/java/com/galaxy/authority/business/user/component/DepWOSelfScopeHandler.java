package com.galaxy.authority.business.user.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.authority.dao.user.IUserDao;
/**
 * 本部门（除自己）
 * @author wangsong
 *
 */
@Component
public class DepWOSelfScopeHandler extends AbstractScopeHandler implements ScopeHandler {
	@Autowired
	IUserDao userDao;
	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 4 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
		Integer userId = getVal(data,"userId");
		List<Integer> userIds = userDao.geDepUserIds(userId);
		if(userIds != null && userIds.size() > 0)
		{
			if(userIds.contains(userId))
			{
				userIds.remove(userId);
			}
		}
		return userIds;
	}

}
