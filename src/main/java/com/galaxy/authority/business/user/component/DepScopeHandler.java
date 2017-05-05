package com.galaxy.authority.business.user.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.authority.dao.user.IUserDao;
/**
 * 本部门所有人
 * @author wangsong
 *
 */
@Component
public class DepScopeHandler extends AbstractScopeHandler implements ScopeHandler {

	@Autowired
	IUserDao userDao;
	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 3 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
		Integer userId = getVal(data,"userId");
		List<Integer> userIds = userDao.geDepUserIds(userId);
		return userIds;
	}

}
