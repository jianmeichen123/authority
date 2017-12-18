package com.galaxy.authority.business.brain.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.dao.role.IRoleDao;

@Repository
public class BrainServiceImpl implements IBrainService{
	//角色
	@Autowired
	private IRoleDao dao;
	
	//根据 用户id 查询返回部门id
	@Override
	public Long selectDepIdByUserId(Map<String, Object> paramMap) {
		return dao.selectDepIdByUserId(paramMap);
	}

}
