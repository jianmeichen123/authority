package com.galaxy.authority.business.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RelRoleUser;
import com.galaxy.authority.bean.role.RoleBean;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.role.IRelRoleUserDao;
import com.galaxy.authority.dao.role.IRoleDao;

@Repository
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleDao dao;
	@Autowired
	private IRelRoleUserDao rdao;
	
	
	/**
	 * 获取角色显示列表
	 */
	@Override
	public Page<UserBean> getRoleList(Map<String, Object> paramMap) {
		Page<UserBean> page = new Page<UserBean>();
		//角色列表
		List<Map<String,Object>> roleList = dao.getRoleList(paramMap);
		for (Map<String, Object> map : roleList) {
			Map<String,Object> userParamMap = new HashMap<String,Object>();
    		
			userParamMap.put("companyId", StaticConst.COMPANY_ID);
    		if(map!=null){
    			userParamMap.put("id", map.get("id"));
    		}
    		//获取角色绑定的账号list
    		List<Map<String,Object>> userList =dao.getUserListByRoleId(userParamMap);
    		if(userList!=null&&userList.size()>0){
    			String lname="";
    			for (Map<String, Object> usermap : userList) {
    				lname+=usermap.get("loginName").toString()+',';
    			}
    			String names = lname.substring(0,lname.length()-1);
    			System.out.println("已关联账号："+userList);
    			map.put("loginName", names);
    		}
		}
		int count = dao.getRoleListCount(paramMap);
		page.setResultCount(count);
		if(roleList!=null){
			page.setMapList(roleList);
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
	
	//递归获取部门以及子部门的用户
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
	
	/**
	 * 检测角色是否有绑定账号
	 */
	@Override
	public boolean checkBindUser(Map<String, Object> paramMap) {
		int count = dao.checkBindUser(paramMap);
		return count>0;
	}
	
	/**
	 * 通过角色id获取绑定账号信息list
	 */
	@Override
	public Page<RelRoleUser> getBindUserInfoListById(Map<String, Object> paramMap) {
		Page<RelRoleUser> page = new Page<RelRoleUser>();
		
		List<Map<String,Object>> datalist =dao.getBindUserInfoListById(paramMap);
		
		int count = rdao.getRelRoleUserListCount(paramMap);
		page.setResultCount(count);
		if(datalist!=null){
			page.setMapList(datalist);
		}
		return page;
	}
	/**
	 * 用户与角色解除绑定
	 */
	@Override
	public int delRelRoleUer(Map<String, Object> map) {
		int count = rdao.delRelRoleUer(map);
		return count;
	}
	/**
	 * 保存用户角色关联信息
	 */
	@Override
	public boolean saveRelRoleUser(List<RelRoleUser> listBean) {
		int count = rdao.saveRelRoleUserBatch(listBean);
		return count>0;
	}
	/**
	 * 获取用户角色关联信息
	 */
	@Override
	public RelRoleUser getRelRoleUser(Map<String, Object> paramMap) {
		RelRoleUser relRoleUser = rdao.getRelRoleUser(paramMap);
		return relRoleUser;
	}
	/**
	 * 更新用户角色关联信息
	 */
	@Override
	public boolean updateRelRoleUser(RelRoleUser relbean) {
		int count = rdao.updateRelRoleUser(relbean);
		return count>0;
	}
	/**
	 * 获取资源树list
	 */
	@Override
	public List<Map<String, Object>> getResourceTreeList(Map<String, Object> paramMap) {
		return dao.getResourceTreeList(paramMap);
	}
	/**
	 * 获取资源树的当前节点以及子节点信息list
	 */
	@Override
	public List<Map<String, Object>> getResourceList(Map<String, Object> paramMap) {

		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		
		//当前节点
		List<Map<String,Object>> plist =new ArrayList<Map<String,Object>>();
		
		plist = dao.getResourceList(paramMap);
		if(list!=null){
			list.addAll(plist);
			//childNodesList(plist,list);
		}
		return list;
	}
	
	//递归获取部门以及子部门的用户
	public void childNodesList(List<Map<String,Object>> plist, List<Map<String, Object>> list) { 
	    if (plist!=null&&plist.size()>0) { 
	    	for (Map<String, Object> map : plist) {
	    		Map<String,Object> paramMap = new HashMap<String,Object>();
	    		//父节点默认：0
	    		paramMap.put("parentId", 0);
	    		paramMap.put("companyId", StaticConst.COMPANY_ID);
	    		if(map!=null){
	    			paramMap.put("parentId", map.get("id"));
	    		}
				List<Map<String,Object>> cList =dao.getResourceList(paramMap);
				if (cList.size()>0) { 
					list.addAll(cList);
					childNodesList(cList,list);
			    }
			}
	    }
	}
	/**
	 * 获取数据范围
	 */
	@Override
	public List<Map<String, Object>> getDataScope(Map<String, Object> paramMap) {
		
		return dao.getDataScope(paramMap);
	}

}
