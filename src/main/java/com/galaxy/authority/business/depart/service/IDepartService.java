package com.galaxy.authority.business.depart.service;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.depart.DepartBean;

public interface IDepartService {
	List<Map<String,Object>> getDepartTreeList(Map<String,Object> paramMap);
	boolean saveDepart(DepartBean departBean);
	List<Map<String,Object>> getDepartList(Map<String,Object> param);
	List<Map<String,Object>> getDepartForComboxTree(Map<String,Object> param);
	boolean updateDepart(DepartBean bean);
	boolean getDepUserCount(Map<String,Object> map);
	boolean delDepartment(Map<String,Object> map);
	Page<Map<String,Object>> getDepartPersonList(Map<String,Object> paramMap);
	List<Map<String,Object>> getLeafDepartList(Map<String,Object> paramMap);
	//判断部门名称是否存在
	int isExitDepartment(String depName);
	List<Map<String,Object>> getCareerLineList();
	List<Map<String, Object>> getDeptIdByDeptName(Map<String, Object> map);
	List<Map<String, Object>> getDeptInfo(Map<String, Object> map);
}
