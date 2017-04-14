package com.galaxy.authority.dao.position;

import java.util.Map;

import com.galaxy.authority.bean.position.RelPosUser;

public interface IRelPosUserDao {
	int saveRelPosUser(RelPosUser bean);
	int delRelPosUser(Map<String,Object> map);
}
