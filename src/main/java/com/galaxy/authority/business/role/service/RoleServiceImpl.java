package com.galaxy.authority.business.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RoleBean;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.role.IRoleDao;

@Repository
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleDao dao;
	
	
	/**
	 * 获取角色显示列表
	 */
	@Override
	public Page<UserBean> getRoleList(Map<String, Object> paramMap) {
		Page<UserBean> page = new Page<UserBean>();
		
		List<Map<String,Object>> dataList = dao.getRoleList(paramMap);
		int count = dao.getRoleListCount(paramMap);
		page.setResultCount(count);
		if(dataList!=null){
			page.setMapList(dataList);
		}
		return page;
	}
	/**
	 * 新增角色
	 */
	@Override
	public boolean saveRole(RoleBean bean) {
		int count = dao.saveRole(bean);
		return (count>0);
	}
	/**
	 * 删除角色
	 */
	@Override
	public int delRoleById(Map<String, Object> paramMap) {
		int count = dao.delRoleById(paramMap);
		return count;
	}
	/**
	 * 通过id获取角色bean
	 */
	@Override
	public RoleBean getRoleById(long id) {
		
		return dao.getRoleById(id);
	}
	/**
	 * 更新角色
	 */
	@Override
	public boolean updateRole(RoleBean bean) {
		return dao.updateRole(bean);
	}
	/**
	 * 通过部门id获取部门人数list
	 */
	@Override
	public List<Map<String, Object>> getUserListByDeptId(Map<String, Object> paramMap) {
		List<Map<String,Object>> deptlist =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> allUserlist =new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> dataList = dao.getUserListByDeptId(paramMap);
		if(dataList!=null){
			deptlist.addAll(dataList);
			digui(dataList,deptlist);
		}
		System.out.println(deptlist);
		for (Map<String, Object> map : dataList) {
			Map<String,Object> deptParamMap = new HashMap<String,Object>();
    		
			deptParamMap.put("parentId", -1L);
			deptParamMap.put("companyId", StaticConst.COMPANY_ID);
    		if(map!=null){
    			deptParamMap.put("id", map.get("id"));
    		}
    		List<Map<String,Object>> userList =dao.getUserNameByDeptId(deptParamMap);
    		if(userList!=null&&userList.size()>0){
    			allUserlist.addAll(userList);
    		}
		}
		System.out.println(allUserlist);
		return allUserlist;
	}
	
	 public void digui(List<Map<String,Object>> dataList, List<Map<String, Object>> deptlist) { 
		    if (dataList!=null&&dataList.size()>0) { 
		    	for (Map<String, Object> map : dataList) {
		    		Map<String,Object> paramMap = new HashMap<String,Object>();
		    		
		    		paramMap.put("parentId", -1L);
		    		paramMap.put("companyId", StaticConst.COMPANY_ID);
		    		if(map!=null){
		    			paramMap.put("parentId", map.get("id"));
		    		}
		    		
					List<Map<String,Object>> allList =dao.getUserListByDeptId(paramMap);
					if (allList.size()>0) { 
						deptlist.addAll(allList);
						digui(allList,deptlist);
				    }
				}
		    }
		}

}
