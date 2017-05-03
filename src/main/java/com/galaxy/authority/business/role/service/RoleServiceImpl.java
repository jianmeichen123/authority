package com.galaxy.authority.business.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.role.RelRoleResource;
import com.galaxy.authority.bean.role.RelRoleScope;
import com.galaxy.authority.bean.role.RelRoleUser;
import com.galaxy.authority.bean.role.RelSpUsers;
import com.galaxy.authority.bean.role.RoleBean;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.role.IRelRoleResourceDao;
import com.galaxy.authority.dao.role.IRelRoleScopeDao;
import com.galaxy.authority.dao.role.IRelRoleUserDao;
import com.galaxy.authority.dao.role.IRelSpUsersDao;
import com.galaxy.authority.dao.role.IRoleDao;

@Repository
public class RoleServiceImpl implements IRoleService{
	//角色
	@Autowired
	private IRoleDao dao;
	//用户与角色关联
	@Autowired
	private IRelRoleUserDao udao;
	//角色与资源关联
	@Autowired
	private IRelRoleResourceDao rdao;
	//角色资源关联与数据范围
	@Autowired
	private IRelRoleScopeDao sdao;
	//角色资源与数据范围关联用户对应
	@Autowired
	private IRelSpUsersDao sudao;
	
	
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
    				lname+=usermap.get("userName").toString()+',';
    			}
    			String names = lname.substring(0,lname.length()-1);
    			map.put("userName", names);
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
		//获取当前部门id，子部门id放在deptlist里
		List<Map<String,Object>> currNode = dao.getcurrNodeByDeptId(paramMap);
		deptlist.addAll(currNode);
		List<Map<String,Object>> dataList = dao.getChildNodesByDeptId(paramMap);
		if(dataList!=null){
			deptlist.addAll(dataList);
			digui(dataList,deptlist);
		}
		//循环获取部门下所有用户
		for (Map<String, Object> map : deptlist) {
			Map<String,Object> deptParamMap = new HashMap<String,Object>();
    		
			deptParamMap.put("parentId", 0);
			deptParamMap.put("companyId", StaticConst.COMPANY_ID);
    		if(map!=null){
    			deptParamMap.put("id", map.get("id"));
    		}
    		List<Map<String,Object>> userList =dao.getUserNameByDeptId(deptParamMap);
    		if(userList!=null&&userList.size()>0){
    			allUserlist.addAll(userList);
    		}
		}
		return allUserlist;
	}
	
	//递归获取部门以及子部门的用户
	public void digui(List<Map<String,Object>> dataList, List<Map<String, Object>> deptlist) { 
	    if (dataList!=null&&dataList.size()>0) { 
	    	for (Map<String, Object> map : dataList) {
	    		Map<String,Object> paramMap = new HashMap<String,Object>();
	    		
	    		paramMap.put("parentId", 0);
	    		paramMap.put("companyId", StaticConst.COMPANY_ID);
	    		if(map!=null){
	    			paramMap.put("parentId", map.get("id"));
	    		}
	    		
				List<Map<String,Object>> allList =dao.getChildNodesByDeptId(paramMap);
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
		
		int count = udao.getRelRoleUserListCount(paramMap);
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
		int count = udao.delRelRoleUer(map);
		return count;
	}
	/**
	 * 保存用户角色关联信息
	 */
	@Override
	public boolean saveRelRoleUser(List<RelRoleUser> listBean) {
		int count = udao.saveRelRoleUserBatch(listBean);
		return count>0;
	}
	/**
	 * 获取用户角色关联信息
	 */
	@Override
	public RelRoleUser getRelRoleUser(Map<String, Object> paramMap) {
		RelRoleUser relRoleUser = udao.getRelRoleUser(paramMap);
		return relRoleUser;
	}
	/**
	 * 更新用户角色关联信息
	 */
	@Override
	public boolean updateRelRoleUser(RelRoleUser relbean) {
		int count = udao.updateRelRoleUser(relbean);
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

		List<Map<String,Object>> list = dao.getResourceList(paramMap);
		if(list!=null){
			return list;
		}
		return null;
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
	/**
	 * 保存角色资源关联信息
	 * @param listBean
	 * @return
	 */
	@Override
	public boolean saveRelRoleResource(List<RelRoleResource> listBean) {
	
		return rdao.saveRelRoleResource(listBean);
	}
	/**
	 * 获取角色资源关联id
	 * @param roleId
	 * @param resId
	 * @return
	 */
	@Override
	public String getRelRoleResId(Map<String, Object> paramMap) {
		String id = rdao.getRelRoleResId(paramMap);
		return id;
	}
	/**
	 * 保存角色资源和数据范围关联信息
	 */
	@Override
	public Boolean saveRelRoleScope(List<RelRoleScope> spListBean) {
		
		return sdao.saveRelRoleScope(spListBean);
	}
	/**
	 * 删除角色资源与数据范围关联表信息
	 */
	@Override
	public int delRelRoleScope(Map<String, Object> scopemap) {
		int count = sdao.delRelRoleScope(scopemap);
		return count;
	}
	/**
	 * 删除角色资源关联信息
	 */
	@Override
	public int delRelRoleResource(Map<String, Object> scopemap) {
		int count = rdao.delRelRoleResource(scopemap);
		return count;
	}
	/**
	 * 获取当前部门以及子部门id和name
	 */
	@Override
	public List<Map<String, Object>> getDeptIdName(Map<String, Object> paramMap) {
		List<Map<String,Object>> deptlist =new ArrayList<Map<String,Object>>();
		//获取当前部门id，子部门id放在deptlist里
		List<Map<String,Object>> currNode = dao.getcurrNodeByDeptId(paramMap);
		deptlist.addAll(currNode);
		List<Map<String,Object>> dataList = dao.getChildNodesByDeptId(paramMap);
		if(dataList!=null){
			deptlist.addAll(dataList);
			digui(dataList,deptlist);
		}
		return deptlist;
	}
	/**
	 * 获取角色资源与范围关联id
	 */
	@Override
	public String getRelRoleScopeId(Map<String, Object> paramMap) {
		String id = sdao.getRelRoleScopeId(paramMap);
		return id;
	}
	/**
	 * 保存角色资源与数据范围，用户的关联信息
	 */
	@Override
	public Boolean saveRelSpUsers(List<RelSpUsers> suListBean) {
		
		return sudao.saveRelSpUsers(suListBean);
	}
	/**
	 * 删除角色资源与数据范围，用户关联信息
	 */
	@Override
	public int delRelSpUsers(Map<String, Object> sm) {
		int count = sudao.delRelSpUsers(sm);
		return count;
	}
	/**
	 * 通过资源id获取关联表信息（用户回显）
	 */
	@Override
	public List<Map<String, Object>> isDisplayByResId(Map<String, Object> mp) {
		List<Map<String,Object>> dataList = dao.isDisplayByResId(mp);
		if(dataList!=null){
			return dataList;
		}
		return null;
	}
	
	/**
	 * 获取对应的用户name或部门name
	 */
	@Override
	public List<Map<String, Object>> showUernameOrDeptName(Map<String, Object> paramMap) {
		List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();
		//解析
		String type = paramMap.get("type").toString();
		String str[] = paramMap.get("typeStr").toString().split(",");
		//部门
		if("0".equals(type)){
			for(int i=0;i<str.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId", StaticConst.COMPANY_ID);
				map.put("deptId", str[i]);
				List<Map<String,Object>> deptList = dao.getDeptName(map);
				 allList.addAll(deptList);
			}
			
		}
		//用户
		if("1".equals(type)){
			for(int i=0;i<str.length;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyId", StaticConst.COMPANY_ID);
				map.put("userId", str[i]);
				List<Map<String,Object>> userList = dao.getUserName(map);
				allList.addAll(userList);
			}
		}
		return allList;
	}
	/**
	 * 获取资源列表
	 */
	@Override
	public List<Map<String, Object>> getResList(Map<String, Object> paramMap) {
		
		List<Map<String,Object>> dataList = this.getResourceList(paramMap);
		
		if(dataList!=null){
			for (Map<String, Object> map : dataList) {
			
				paramMap.put("resId", map.get("id"));
				//用户回显权限设置
				List<Map<String,Object>> list = this.isDisplayByResId(paramMap);
				if(list!=null){
					for (Map<String, Object> map1 : list) {
						//复选框（inputflag，1：选择，0：未选择）
						if(!"".equals(map1.get("rId")) && map1.get("rId")!=null){
							map.put("inputflag", 1);
						}else{
							map.put("inputflag", 0);
						}
						//下拉框（selectflag，1：选择，0：未选择 selectvalue 选择的内容）
						if(!"".equals(map1.get("sId")) && map1.get("sId")!=null){
							map.put("selectflag", 1);
							map.put("selectValue", map1.get("spId"));
						}else{
							map.put("selectflag", 0);
						}
						//其他选择内容
						if(!"".equals(map1.get("spId")) && map1.get("spId")!=null && map1.get("spId").equals(6)){
							if(map1.get("isDep")!=null&&map1.get("otherId")!=null){
								map.put("isDepFlag", map1.get("isDep"));
								map.put("resIdFlag", map1.get("isDep")+"#"+map1.get("otherId"));
							}
						}
					}
				}
			}
		}
		return dataList;
	}
	/**
	 * 保存权限设置
	 */
	@Override
	public boolean saveResource(Map<String, Object> paramMap) {
		Boolean res=true;
		String strCheck="";
		
		//角色，资源关联信息表
		List<RelRoleResource> listBean = new ArrayList<RelRoleResource>();
		RelRoleResource bean = null;
		//角色资源，数据范围关联信息表
		List<RelRoleScope> spListBean = new ArrayList<RelRoleScope>();
		RelRoleScope spbean = null;
		//角色资源数据范围，用户关联信息表
		List<RelSpUsers> suListBean = new ArrayList<RelSpUsers>();
		RelSpUsers subean = null;
		
		try {
			//当前资源获取当前以及资源id，且遍历list
			List<Map<String,Object>> dataList = this.getResourceList(paramMap);
					
			if(dataList!=null&&dataList.size()>0){
				for(Map<String,Object> dataMap:dataList){
					//通过角色id，资源id，获取角色资源关联id
					String  resid =dataMap.get("id").toString();
					paramMap.put("resId", resid);
					//获取角色资源关联id
					String id = this.getRelRoleResId(paramMap);
					paramMap.put("roleResId", id);
					//获取角色资源与数据范围关联id
					String sid = this.getRelRoleScopeId(paramMap);
					paramMap.put("relRoleScopeId", sid);
					if(!"".equals(id)&&id!=null){
						//根据角色资源关联id，删除scope关联表信息
						this.delRelRoleScope(paramMap);
						//删除成功后，在删除角色资源关联关系
						this.delRelRoleResource(paramMap);
						if(!"".equals(sid)&&sid!=null){
							//删除角色资源与数据范围成功之后，删除角色资源与数据范围，用户关联信息
							this.delRelSpUsers(paramMap);
						}
					}
				}
			}
			
			strCheck = paramMap.get("strCheck").toString();
			//多个复选框，以逗号拆分   164:6:0#31,34 ; 165:6:1#56,59;
			String str[] = strCheck.split(";");					
			for(int i=0;i<str.length;i++){
				//资源id：数据范围id 或  资源id：数据范围id：其他
				String str1[] = str[i].split(":");				
				if(!"no".equals(str1[1]) && str1[1]!=null){
					//角色资源关系表
					bean = new RelRoleResource();
					bean.setRoleId(Integer.valueOf(paramMap.get("roleId").toString()));
					bean.setResId(Integer.valueOf(str1[0]));
					bean.setCompanyId(StaticConst.COMPANY_ID);
					//临时的数据范围id
					bean.setTempSpId(Integer.valueOf(str1[1]));
					//判断是否选择‘其他’
					if("6".equals(str1[1])){
						//临时其他选项内容
						bean.setTempOtherStr(str1[2]);
					}
					listBean.add(bean);
				}
			}
					
			if(listBean!=null&&listBean.size()>0){
				//保存角色，资源关联信息
				res =this.saveRelRoleResource(listBean);
			}
			//角色资源保存成功，获取关联id
			if(res){
				for(RelRoleResource rBean:listBean){
					//通过角色id，资源id，获取角色资源关联id
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("companyId", StaticConst.COMPANY_ID);
					map1.put("roleId", rBean.getRoleId());
					map1.put("resId", rBean.getResId());
					String id = this.getRelRoleResId(map1);
					//选择数据范围
					if(rBean.getTempSpId()!=0){
						spbean = new RelRoleScope();
						spbean.setRoleResId(Integer.valueOf(id));
						spbean.setSpId(rBean.getTempSpId());
						//临时其他选项内容
						spbean.setTempOtherStr(rBean.getTempOtherStr());
						spbean.setCompanyId(StaticConst.COMPANY_ID);
						spListBean.add(spbean);
						
					}
				}
				if(spListBean!=null && spListBean.size()>0){
					//保存角色资源,数据范围关联信息
					res =this.saveRelRoleScope(spListBean);
					//保存角色资源,数据范围关联信息成功之后
					if(res){
						for(RelRoleScope sBean:spListBean){
							//通过角色id，资源id，获取角色资源关联id
							Map<String,Object> map2 = new HashMap<String,Object>();
							map2.put("companyId", StaticConst.COMPANY_ID);
							map2.put("spId", sBean.getSpId());
							map2.put("roleResId", sBean.getRoleResId());
							String id = this.getRelRoleScopeId(map2);
							//选择数据范围
							if(sBean.getTempOtherStr()!=null){
								//选择类型#类型对应的id  0#30,31,34
								String str2[] = sBean.getTempOtherStr().split("#");	
								if(!"".equals(str2[1]) && str2[1]!=null){
									subean = new RelSpUsers();
									subean.setOtherId(str2[1]);
									subean.setRelRoleScopeId(Integer.valueOf(id));
									subean.setIsDep(Integer.valueOf(str2[0]));
									subean.setCompanyId(StaticConst.COMPANY_ID);
									suListBean.add(subean);
								}
								
							}
						}
						if(suListBean!=null && suListBean.size()>0){
							//保存角色资源与数据范围，用户关联信息
							res =this.saveRelSpUsers(suListBean);
						}
						if(res){
							return true;
						}
						return true;
					}
				}
				return true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
