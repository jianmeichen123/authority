package com.galaxy.authority.business.role.controller;

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
		//RoleBean roleBean = null;
		if(bean!=null){
			bean.setCompanyId(StaticConst.COMPANY_ID);
			if(CUtils.get().stringIsNotEmpty(bean.getId())&&bean.getId()!=0){
				bean.setUpdateTime(DateUtil.getMillis(new Date()));
				result.setSuccess(service.updateRole(bean));
			}else{
				result.setSuccess(service.saveRole(bean));
			}
			/*//id不为空，获取角色数据
			if(bean.getId()!=0){
				//是否存在数据
				roleBean =service.getRoleById(bean.getId());
			}
			if(roleBean!=null){
				result.setSuccess(service.updateRole(roleBean));
			}else{
				result.setSuccess(service.saveRole(bean));
			}*/
		}
		return result;
	}
	
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
}
