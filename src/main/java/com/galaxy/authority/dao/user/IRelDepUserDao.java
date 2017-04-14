package com.galaxy.authority.dao.user;

import java.util.Map;
import com.galaxy.authority.bean.depart.RelDepUser;

public interface IRelDepUserDao {
	int saveRelDepUser(RelDepUser rdu);
	int delRelDepUser(Map<String,Object> map);
}
