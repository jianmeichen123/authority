package com.galaxy.authority.business.user.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService service;
	
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
