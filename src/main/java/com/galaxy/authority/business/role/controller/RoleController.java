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
import org.springframework.web.bind.annotation.RequestParam;
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
	 * 获取角色列表
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
			//重名判断
			int count = service.isExitRole(bean.getRoleName());
			if(CUtils.get().stringIsNotEmpty(bean.getId())&&bean.getId()!=0){
				if(count>0&&!bean.getRoleName().equals(bean.getOldRoleName())){
					result.setSuccess(false);
					result.setMessage("角色已经存在");
				}else{
					//更新
					bean.setUpdateTime(DateUtil.getMillis(new Date()));
					result.setSuccess(service.updateRole(bean));
				}
			}else{
				//保存
				if(count<1){
					result.setSuccess(service.saveRole(bean));
				}else{
					result.setSuccess(false);
					result.setMessage("角色已经存在");
				}
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
		paramMap.put("parentId", 0);
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
	
	/**
	 * 显示资源树
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getResourceTree")
	@ResponseBody
	public Object getResourceTree(@RequestBody String paramString){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//父节点：0
		paramMap.put("parentId", 0);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		List<Map<String,Object>> list = service.getResourceTreeList(paramMap);
		createResourceChild(list,"ztree");
		return list;
	}
	
	/**
	 * 生成树型列表
	 * @param depList
	 */
	private void createResourceChild(List<Map<String,Object>> list,String state){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				paramMap.put("parentId", list.get(i).get("id"));
				paramMap.put("companyId", StaticConst.COMPANY_ID);
				List<Map<String,Object>> dataList = null;
				if("ztree".equals(state)){
					dataList = service.getResourceTreeList(paramMap);
				}
				if(dataList!=null && !dataList.isEmpty()){
					createResourceChild(dataList,state);
					list.get(i).put("children", dataList);
				}
			}
		}
	}
	
	/**
	 * 获取资源树的当前节点以及子节点信息list
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getResourceList")
	@ResponseBody
	public Object getResourceList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//父节点：0
		paramMap.put("parentId", 0);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		//解析请求信息
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")&& paramJson.has("roleId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
				paramMap.put("roleId", paramJson.getLong("roleId"));
			}
		}
		try {
			List<Map<String,Object>> dataList = service.getResList(paramMap);
			if(dataList!=null){
				result.setSuccess(true);
				result.setValue(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取数据范围
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDataScope")
	@ResponseBody
	public Object getDataScope(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		try {
			//获取数据范围信息
			List<Map<String,Object>> dataList = service.getDataScope(paramMap);
			if(dataList!=null){
				result.setSuccess(true);
				result.setValue(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 保存权限设置
	 * @param paramString
	 * @return
	 */
	@RequestMapping("saveResource")
	@ResponseBody
	public Object saveResource(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId", 0);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		//解析请求数据
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("strCheck")&&paramJson.has("roleId")&&paramJson.has("resourceId")){
				paramMap.put("strCheck", paramJson.getString("strCheck"));
				paramMap.put("roleId", paramJson.getLong("roleId"));
				paramMap.put("parentId", paramJson.getLong("resourceId"));
			}
		}
		try {
			result.setSuccess(service.saveResource(paramMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取当前部门以及子部门id和name
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDeptIdName")
	@ResponseBody
	public Object getDeptIdName(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId", 0);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		try {
			Map<String, Object> map11  = new HashMap<String, Object>();
			List<Map<String,Object>> dataList = service.getDeptIdName(paramMap);
			for (Map<String, Object> map : dataList) {
				map11.put(map.get("id").toString(), map.get("name"));
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
	 * 获取对应的用户name或部门name
	 * @param paramString
	 * @return
	 */
	@RequestMapping("showUernameOrDeptName")
	@ResponseBody
	public Object showUernameOrDeptName(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("type") && paramJson.has("typeStr")){
				paramMap.put("type", paramJson.getLong("type"));
				paramMap.put("typeStr", paramJson.getString("typeStr"));
			}
		}
		try {
			List<Map<String,Object>> dataList = service.showUernameOrDeptName(paramMap);
			if(dataList!=null){
				result.setSuccess(true);
				result.setValue(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取用户的角色ID
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@RequestMapping("selectRoleIdByUserId")
	@ResponseBody
	public List<Long> selectRoleIdByUserId(@RequestParam Long userId, @RequestParam String companyId)
	{
		Map<String,Object> query = new HashMap<>();
		query.put("userId", userId);
		query.put("companyId",companyId);
		return service.selectRoleIdByUserId(query);
	}
	
	/**
	 * 获取角色关联账号信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping("showUerName")
	@ResponseBody
	public Object showUerName(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId",StaticConst.COMPANY_ID);
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("roleId")){
				paramMap.put("roleId", paramJson.getLong("roleId"));
			}
		}
		try {
			List<Map<String,Object>> dataList = service.showUerName(paramMap);
			if(dataList!=null){
				result.setSuccess(true);
				result.setValue(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
