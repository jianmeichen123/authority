package com.galaxy.authority.business.role.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.role.RelRoleUser;
import com.galaxy.authority.bean.role.RoleBean;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.business.role.service.IRoleService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private IRoleService service;
	/**
	 * 获取角色显示列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public Object getRoleList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		int pageNo = CUtils.get().object2Integer(map.get("pageNo"));
		int pageSize = CUtils.get().object2Integer(map.get("pageSize"));
		int startNo = (pageNo-1)*pageSize;
		map.put("startNo", startNo<0?0:startNo);
		
		map.put("companyId", StaticConst.COMPANY_ID);
		Page<UserBean> page = service.getRoleList(map);
		
		if(page!=null && page.getMapList()!=null){
			result.setSuccess(true);
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("total", page.getResultCount());
			dataMap.put("rows", page.getMapList());
			result.setValue(dataMap);
		}
		return result;
	}
	/**
	 * 新增/编辑角色
	 * @param bean
	 * @return
	 */
	@RequestMapping("saveRole")
	@ResponseBody
	public Object saveRole(@RequestBody RoleBean bean){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		if(bean!=null){
			bean.setCompanyId(StaticConst.COMPANY_ID);
			if(CUtils.get().stringIsNotEmpty(bean.getId())&&bean.getId()!=0){
				bean.setUpdateTime(DateUtil.getMillis(new Date()));
				result.setSuccess(service.updateRole(bean));
			}else{
				result.setSuccess(service.saveRole(bean));
			}
		}
		return result;
	}
	/**
	 * 删除角色
	 * @param paramString
	 * @return
	 */
	@RequestMapping("delRoleById")
	@ResponseBody
	public Object delRoleById(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		
		try {
			int res = service.delRoleById(map);
			
			if(res==1){
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	/**
	 * 通过部门id获取部门人数list
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getUserListByDeptId")
	@ResponseBody
	public Object getUserListByDeptId(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId", -1L);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		try {
			Map<String, Object> map11  = new HashMap<String, Object>();
			List<Map<String,Object>> dataList = service.getUserListByDeptId(paramMap);
			for (Map<String, Object> map : dataList) {
				map11.put(map.get("id").toString(), map.get("user_name"));
			}
			
			if(dataList!=null){
				result.setSuccess(true);
				result.setValue(map11);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 通过角色id获取绑定账号信息list
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getBindUserInfoListById")
	@ResponseBody
	public Object getBindUserInfoListById(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		int pageNo = CUtils.get().object2Integer(paramMap.get("pageNo"));
		int pageSize = CUtils.get().object2Integer(paramMap.get("pageSize"));
		int startNo = (pageNo-1)*pageSize;
		paramMap.put("startNo", startNo<0?0:startNo);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("id")){
				paramMap.put("id", paramJson.getString("id"));
			}
		}
		try {
			Page<RelRoleUser> page = service.getBindUserInfoListById(paramMap);
			
			if(page!=null && page.getMapList()!=null){
				result.setSuccess(true);
				
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("total", page.getResultCount());
				dataMap.put("rows", page.getMapList());
				result.setValue(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	/**
	 * 用户与角色解除绑定
	 * @param paramString
	 * @return
	 */
	@RequestMapping("delRelRoleUer")
	@ResponseBody
	public Object delRelRoleUer(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		try {
			int res = service.delRelRoleUer(map);
			if(res==1){
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	/**
	 * 检测角色是否有绑定账号
	 * @param mapString
	 * @return
	 */
	@RequestMapping("checkBindUser")
	@ResponseBody
	public Object checkBindUser(@RequestBody String mapString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(true);
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(mapString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().mapIsNotEmpty(paramMap)){
			if(service.checkBindUser(paramMap)){
				result.setSuccess(false);
			}
		}
		return result;
	}
	/**
	 * 保存用户角色关联信息
	 * @param bean
	 * @return
	 */
	@RequestMapping("saveRelRoleUser")
	@ResponseBody
	public Object saveRelRoleUser(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		String userIdStr="";
		String roleId="";
		List<RelRoleUser> listBean = new ArrayList<RelRoleUser>();
		RelRoleUser bean = null;
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("userIdStr")&&paramJson.has("roleId")){
				roleId =paramJson.getString("roleId");
				userIdStr = paramJson.getString("userIdStr");
			}
		}
		
		String str[] = userIdStr.split(",");
		for(int i=0;i<str.length;i++){
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("roleId", roleId);
			paramMap.put("userId", str[i]);
			paramMap.put("companyId", StaticConst.COMPANY_ID);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				RelRoleUser relbean = service.getRelRoleUser(paramMap);
				if(relbean!=null){
					relbean.setUpdateTime(DateUtil.getMillis(new Date()));
					result.setSuccess(service.updateRelRoleUser(relbean));
				}else{
					bean = new RelRoleUser();
					bean.setRoleId(roleId);
					bean.setUserId(str[i]);
					bean.setCompanyId(StaticConst.COMPANY_ID);
					listBean.add(bean);
				}
			}
		}
		
		if(listBean!=null&&listBean.size()>0){
			result.setSuccess(service.saveRelRoleUser(listBean));
		}
		return result;
	}
}
