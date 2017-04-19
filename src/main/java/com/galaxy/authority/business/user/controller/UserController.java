package com.galaxy.authority.business.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.Md5Utils;
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
			String password = CUtils.get().object2String(map.get("password"));
			String originPassword = Md5Utils.md5Crypt(password);
			map.put("originPassword", originPassword);
		}
		
		map.put("companyId", StaticConst.COMPANY_ID);
		boolean ifSuccess = service.resetPassword(map);
		result.setSuccess(ifSuccess);
		return result;
	}

}
