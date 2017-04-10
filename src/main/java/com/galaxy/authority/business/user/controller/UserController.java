package com.galaxy.authority.business.user.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;
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
		Page<UserBean> page = service.getUserList(map);
		
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
	 * 新增用户
	 * @param userString
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object saveUser(@RequestBody String userString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		boolean ifSuccess = service.saveUser(map);
		
		result.setSuccess(ifSuccess);
		
		return result;
	}
	

}
