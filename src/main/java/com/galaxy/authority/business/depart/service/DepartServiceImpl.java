package com.galaxy.authority.business.depart.service;

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
	
	
	
}
