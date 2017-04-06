package com.galaxy.authority.dao.depart;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.depart.DepartBean;

public interface IDepartDao {
	DepartBean getDepartById(long id);
	List<Map<String,Object>> getDepartTreeList(Map<String,Object> param);
	int saveDepart(DepartBean departBean);
	List<Map<String,Object>> getDepartList(Map<String,Object> param);
}
