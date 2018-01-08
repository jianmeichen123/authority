package com.galaxy.authority.business.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.galaxy.authority.common.redisconfig.IRedisCache;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService service;
	
	@Autowired
	private IRedisCache<String, Object> cache;
	
	@RequestMapping("loginself")
	@ResponseBody
	public Object loginSelf(HttpServletRequest request,@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		String passWord = CUtils.get().object2String(paramMap.get("passWord"));
		String base64Password = PWDUtils.genernateNewPassword(passWord);
		paramMap.put("base64Password", base64Password);
		
		String sessionId = request.getSession().getId();
		Map<String,Object> userMap = service.loginSelf(paramMap);
		
		if(userMap!=null && !userMap.isEmpty()){
			result.setSuccess(true);
			result.setValue(userMap);
			cache.put(sessionId,sessionId);
			userMap.put("sessionId", sessionId);
		}
		return result;
	}
	
	@RequestMapping("logoutSelf")
	@ResponseBody
	public Object logoutSelf(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		if(paramMap!=null && paramMap.containsKey("sessionId")){
			String sessionId = CUtils.get().object2String(paramMap.get("sessionId"));
			cache.remove(sessionId);
			result.setSuccess(true);
		}
		return result;
	}
	
	
	
	
	//登录功能
	@RequestMapping("/userLogin")
	@ResponseBody
	@ApiOperation(value="用户登录", notes="")
	@ApiImplicitParam(name = "paramString", value = "用户信息{userName:xxx,passWord:xxx,companyId:companyId}", required = true)
	public Object userLogin(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		String username="";
		String password="";
		String productType="0";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if (logger.isInfoEnabled())
		{
			logger.info("ReciveInfo : %s"+paramString);
		}
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("userName")&&paramJson.has("passWord")){
				//加密password
				password = PWDUtils.genernateNewPassword(paramJson.getString("passWord")); 
				username = paramJson.getString("userName");
				
				paramMap.put("userName", username);
				paramMap.put("passWord", password);
			}
			if(paramJson!=null && paramJson.has("productType"))
			{
				productType = paramJson.getString("productType");
				paramMap.put("productType", productType);
			}
		}
		//判断用户名和里面里面是否为空
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			result.setSuccess(false);
			result.setMessage("用户名或密码不能为空！");
		}else{
			if (logger.isInfoEnabled())
			{
				logger.info("Start query user info");
			}
			Map<String,Object> userInfo = service.userLogin(paramMap);
			if (logger.isInfoEnabled())
			{
				logger.info("Get user info:"+userInfo);
			}
			if(userInfo != null && userInfo.size() >0){
				result.setSuccess(true);
				result.setValue(userInfo);
				result.setMessage("登录成功！");
			}else{
				result.setSuccess(false);
				result.setMessage("用户名或密码错误！");
			}
		}
		if (logger.isInfoEnabled())
		{
			logger.info("Result : %s"+result.toString());
		}
		return result;
	}
	
		//APP登录功能
		@RequestMapping("/userLoginForApp")
		@ResponseBody
		public Object userLoginForApp(@RequestBody String paramString){
			ResultBean result = ResultBean.instance();
			result.setSuccess(false);
			String username="";
			String password="";
			String productType="0";
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
				if(paramJson!=null && paramJson.has("productType"))
				{
					productType = paramJson.getString("productType");
					paramMap.put("productType", productType);
				}
			}
			//判断用户名和里面里面是否为空
			if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
				result.setSuccess(false);
				result.setMessage("用户名或密码不能为空！");
			}else{
				Map<String,Object> userInfo = service.userLoginForApp(paramMap);
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
		
		
		//获取用户数据范围
		@RequestMapping("/allResourceToUser")
		@ResponseBody
		public Object allResourceToUser(@RequestBody String paramString){
			ResultBean result = ResultBean.instance();
			result.setSuccess(false);
			
			try {
				Map<String, Object> paramMap = CUtils.get().jsonString2map(paramString);
				Map<String,Object> userInfo = service.allResourceToUser(paramMap);
				if(userInfo != null && userInfo.size() >0){
					result.setSuccess(true);
					result.setValue(userInfo);
				}else{
					result.setSuccess(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
}
