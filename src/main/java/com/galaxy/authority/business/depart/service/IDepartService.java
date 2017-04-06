package com.galaxy.authority.business.depart.service;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.depart.DepartBean;

public interface IDepartService {
	DepartBean getDepartmentById(long id);
	List<Map<String,Object>> getDepartTreeList(Map<String,Object> paramMap);
	int saveDepart(DepartBean departBean);
	List<Map<String,Object>> getDepartList(Map<String,Object> param);
	List<Map<String,Object>> getDepartForComboxTree(Map<String,Object> param);
}
