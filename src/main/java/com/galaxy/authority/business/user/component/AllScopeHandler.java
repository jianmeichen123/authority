package com.galaxy.authority.business.user.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxy.authority.dao.user.IUserDao;

/**
 * 所有人-包含禁用用户
 * @author wangsong
 *
 */
@Component
public class AllScopeHandler extends AbstractScopeHandler implements ScopeHandler
{
	@Autowired
	IUserDao userDao;
	@Override
	public boolean support(Object data)
	{
		Integer spId = getVal(data,"spId");
		return 7 == spId;
	}

	@Override
	public List<Integer> handle(Object data)
	{
		return userDao.getAllUserIds();
	}
}
