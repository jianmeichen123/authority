package com.galaxy.authority.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.user.TestUserBean;

@Controller
@RequestMapping("/index") 
public class IndexController {
	
	
	@RequestMapping
	@ResponseBody
	public Object index(){
		TestUserBean userBean = new TestUserBean();
		userBean.setName("tdj");
		userBean.setEmail("tdjamtam@qq.com");
		
		//service.saveUser(userBean);
		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("tdj", "tdjgamtma");
//		System.out.println(CUtils.get().map2JSONObject(map).toString());
		
		
		
		

		
		return userBean;
	}
}
