package com.galaxy.authority.business.depart.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.galaxy.authority.bean.depart.DepartBean;
import com.galaxy.authority.dao.depart.IDepartDao;

@Repository
public class DepartServiceImpl implements IDepartService{
	
	@Autowired
	private IDepartDao dao;

	@Override
	public DepartBean getDepartmentById(long id) {
		return dao.getDepartById(id);
	}

	@Override
	public List<Map<String,Object>> getDepartTreeList(Map<String,Object> paramMap) {
		return dao.getDepartTreeList(paramMap);
	}

	@Override
	public boolean saveDepart(DepartBean departBean) {
		return dao.saveDepart(departBean)>0;
	}

	@Override
	public List<Map<String, Object>> getDepartList(Map<String, Object> param) {
		return dao.getDepartList(param);
	}

	@Override
	public List<Map<String, Object>> getDepartForComboxTree(Map<String, Object> param) {
		return dao.getDepartForComboxTree(param);
	}
	
	
	
	
	
	
}
