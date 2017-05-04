package com.galaxy.authority.business.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.business.login.service.ILoginService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.PWDUtils;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private ILoginService service;
	
	//登录功能
	@RequestMapping("/userLogin")
	@ResponseBody
	public Object userLogin(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		String username="";
		String password="";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("userName")&&paramJson.has("passWord")){
				//加密password
				password = PWDUtils.genernateNewPassword(paramJson.getString("passWord")); 
				username = paramJson.getString("userName");
				
				paramMap.put("userName", username);
				paramMap.put("passWord", password);
			}
		}
		//判断用户名和里面里面是否为空
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			result.setSuccess(false);
			result.setMessage("用户名或密码不能为空！");
		}else{
			Map<String,Object> userInfo = service.userLogin(paramMap);
			if(userInfo != null && userInfo.size() >0){
				result.setSuccess(true);
				result.setValue(userInfo);
				result.setMessage("登录成功！");
			}else{
				result.setSuccess(false);
				result.setMessage("用户名或密码错误！");
			}
		}
		
		return result;
	}
	
	
}
