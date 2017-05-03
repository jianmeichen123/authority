package com.galaxy.authority.business.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.authority.InitService;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService service;
	
	@RequestMapping("getUserList")
	@ResponseBody
	public Object getUserList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		int pageNo = CUtils.get().object2Integer(map.get("pageNo"));
		int pageSize = CUtils.get().object2Integer(map.get("pageSize"));
		
		int startNo = (pageNo-1)*pageSize;
		map.put("startNo", startNo<0?0:startNo);
		map.put("companyId", StaticConst.COMPANY_ID);
		Page<Map<String, Object>> page = service.getUserList(map);
		
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
	 * 新增或编辑用户
	 * @param userString
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public Object saveUser(@RequestBody String userString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		String userId = CUtils.get().object2String(map.get("userId"));
		map.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().stringIsNotEmpty(userId)){
			map.put("updateTime", DateUtil.getMillis(new Date()));
			result.setSuccess(service.updateUser(map));
		}else{
			result.setSuccess(service.saveUser(map));
		}
		
		InitService.get().initdepartUser(StaticConst.COMPANY_ID);
		return result;
	}
	
	/**
	 * 禁用或启用
	 */
	@RequestMapping("outtage")
	@ResponseBody
	public Object outtageUser(@RequestBody String userString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		map.put("companyId", StaticConst.COMPANY_ID);
		boolean ifSuccess = service.outtageUser(map);
		result.setSuccess(ifSuccess);
		InitService.get().initdepartUser(StaticConst.COMPANY_ID);
		return result;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object deleteUser(@RequestBody String userString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		map.put("companyId", StaticConst.COMPANY_ID);
		boolean ifSuccess = service.deleteUser(map);
		result.setSuccess(ifSuccess);
		InitService.get().initdepartUser(StaticConst.COMPANY_ID);
		return result;
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping("resetPassword")
	@ResponseBody
	public Object resetPassword(@RequestBody String userString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		
		if(CUtils.get().mapIsNotEmpty(map)){
			//String password = CUtils.get().object2String(map.get("password"));
			//String originPassword = Md5Utils.md5Crypt(password);
			map.put("userId", CUtils.get().object2String(map.get("userId")));
		}
		
		map.put("companyId", StaticConst.COMPANY_ID);
		boolean ifSuccess = service.resetPassword(map);
		result.setSuccess(ifSuccess);
		return result;
	}
	
	/*---------------------对外服务接口----------------------*/
	/**
	 * 根据传入的关键字模糊查询用户并返回列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("findUserByName")
	@ResponseBody
	public Object findUserByName(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(paramMap.containsKey("userName")){
			String userName = CUtils.get().object2String(paramMap.get("userName"));
			paramMap.put("userKey", "%"+userName+"%");
		}
		
		List<Map<String,Object>> dataList = service.findUserByName(paramMap);
		if(CUtils.get().listIsNotEmpty(dataList)){
			result.setSuccess(true);
			result.setValue(dataList);
		}
		return result;
	}
	
	/**
	 * 传入用户ID返回用户信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getUserById")
	@ResponseBody
	public Object getUserById(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		Map<String,Object> userMap = service.getUserById(paramMap);
		if(CUtils.get().mapIsNotEmpty(userMap)){
			result.setSuccess(true);
			result.setValue(userMap);
		}
		return result;
	}
	
	/**
	 * 根据传入的部门ID返回对应的所有用户
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getUsersByDepId")
	@ResponseBody
	public Object getUsersByDepId(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		String depId = CUtils.get().object2String(paramMap.get("depId"));
		if(CUtils.get().stringIsNotEmpty(depId)){
			if(StaticConst.userMap.containsKey(depId)){
				result.setSuccess(true);
				result.setValue(StaticConst.userMap.get(depId));
			}
		}
		return result;
	}
	
	/**
	 * 根据传入的用户的ID集合返回对应的所有用户
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getUserByIds")
	@ResponseBody
	public Object getUserByIds(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(paramMap.containsKey("userIds")){
			String temp = CUtils.get().object2String(paramMap.get("userIds"));
			ObjectMapper mapper = new ObjectMapper();
			try{
				@SuppressWarnings("unchecked")
				List<Long> idsList = mapper.readValue(temp, List.class);
				paramMap.put("userIdList", idsList);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		List<Map<String,Object>> dataList = service.getUserByIds(paramMap);
		if(CUtils.get().listIsNotEmpty(dataList)){
			result.setSuccess(true);
			result.setValue(dataList);
		}
		return result;
	}
	
	/**
	 * 传入用户ID返回用户信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getUsersByKey")
	@ResponseBody
	public Object getUsersByKey(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(paramMap.containsKey("userKey")){
			String userName = CUtils.get().object2String(paramMap.get("userKey"));
			paramMap.put("userKey", "%"+userName+"%");
		}
		
		List<Map<String,Object>> userList = service.getUsersByKey(paramMap);
		if(CUtils.get().listIsNotEmpty(userList)){
			result.setSuccess(true);
			result.setValue(userList);
		}
		return result;
	}
	
	
	

}
