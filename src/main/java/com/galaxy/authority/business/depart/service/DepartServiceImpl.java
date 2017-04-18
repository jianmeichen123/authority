package com.galaxy.authority.business.depart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.depart.DepartBean;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.depart.IDepartDao;
import com.galaxy.authority.dao.user.IUserDao;

@Repository
public class DepartServiceImpl implements IDepartService{
	private Map<String,Object> paramTreeMap = new HashMap<String,Object>();
	
	@Autowired
	private IDepartDao dao;
	
	@Autowired
	private IUserDao uDao;

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

	@Override
	public boolean updateDepart(DepartBean bean) {
		return dao.updateDepart(bean)>0;
	}
	
	@Override
	public boolean delDepartment(Map<String, Object> map) {
		return dao.delDepartment(map)>0;
	}

	@Override
	public boolean getDepUserCount(Map<String, Object> map) {
		long depId = CUtils.get().object2Long(map.get("depId"));
		long companyId = CUtils.get().object2Long(map.get("companyId"));
		boolean flag = false;
		
		if(CUtils.get().stringIsNotEmpty(depId)){
			List<Long> idList = new ArrayList<Long>();
			createDepartChild(depId,idList);
			
			//查询该部门及所有子部门下是否存在绑定的用户
			if(CUtils.get().listIsNotEmpty(idList)){
				//查询用户数量
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("depIds", idList);
				paramMap.put("companyId",companyId);
				
				flag = dao.getDepUserCount(paramMap)>0;
			}
			
			//查询该部门下是否存在子部门
			flag = dao.getChildDepCount(map)>0;
			
		}
		return flag;
	}
	
	/**
	 * 生成树型列表
	 * @param depList
	 */
	private void createDepartChild(long depId,List<Long> idList){
		if(!idList.contains(depId)){
			idList.add(depId);
		}
		
		paramTreeMap.clear();
		paramTreeMap.put("parentId", depId);
		paramTreeMap.put("companyId", StaticConst.COMPANY_ID);
		List<Map<String,Object>> depList =  getDepartTreeList(paramTreeMap);
	
		if(CUtils.get().listIsNotEmpty(depList)){
			for(int i=0;i<depList.size();i++){
				idList.add(CUtils.get().object2Long(depList.get(i).get("id")));
				createDepartChild(CUtils.get().object2Long(depList.get(i).get("id")),idList);
			}
		}
	}

	@Override
	public Page<Map<String, Object>> getDepartPersonList(Map<String, Object> paramMap) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		
		List<Map<String,Object>> dataList = uDao.getUserList(paramMap);
		int count = uDao.getUserListCount(paramMap);
		page.setResultCount(count);
		if(dataList!=null){
			page.setMapList(dataList);
		}
		return page;
	}
	
	
}
