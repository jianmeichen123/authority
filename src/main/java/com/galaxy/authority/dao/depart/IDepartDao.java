package com.galaxy.authority.dao.depart;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.depart.DepartBean;

public interface IDepartDao {
	List<Map<String,Object>> getDepartTreeList(Map<String,Object> param);
	int saveDepart(DepartBean departBean);
	List<Map<String,Object>> getDepartList(Map<String,Object> param);
	List<Map<String,Object>> getDepartForComboxTree(Map<String,Object> param);
	int updateDepart(DepartBean bean);
	int getDepUserCount(Map<String,Object> map);
	int delDepartment(Map<String,Object> map);
	int getChildDepCount(Map<String,Object> map);
}
