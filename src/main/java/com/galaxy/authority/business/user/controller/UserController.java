package com.galaxy.authority.business.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxy.authority.InitService;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.PWDUtils;
import com.galaxy.authority.common.PropertiesUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.common.mail.MailTemplateUtils;
import com.galaxy.authority.common.mail.PlaceholderConfigurer;
import com.galaxy.authority.common.mail.SimpleMailSender;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
		result.setSuccess(false);
		int retValue = 0;
		String loginName = "";
		String oldLoginName = "";
		//生成原密码
		String oriPwd = PWDUtils.genRandomNum(6);
				
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		String userId = CUtils.get().object2String(map.get("userId"));
		map.put("companyId", StaticConst.COMPANY_ID);
		map.put("oriPwd", oriPwd);
		
		if(map.get("loginName")!=null){
			loginName =CUtils.get().object2String(map.get("loginName"));
		}
		if(map.get("oldLoginName")!=null){
			oldLoginName =CUtils.get().object2String(map.get("oldLoginName"));
		}
		int count = service.isExitUser(loginName);
		
		if(CUtils.get().stringIsNotEmpty(userId)){
			if(count>0&&!loginName.equals(oldLoginName)){
				result.setSuccess(false);
				result.setMessage("登录账号已经存在");
			}else{
				map.put("updateTime", DateUtil.getMillis(new Date()));
				result.setSuccess(service.updateUser(map));
			}
		}else{
			//保存
			if(count<1){
				if(service.saveUser(map)){
					retValue=1;
				}
			}else{
				result.setSuccess(false);
				result.setMessage("登录账号已经存在");
			}
			
		}
		
		InitService.get().initdepartUser(StaticConst.COMPANY_ID);
		
		if (retValue >0) {
			//登陆地址
			Properties property = PropertiesUtils.getProperties(StaticConst.MAIL_CONFIG_FILE);
			String loginUrl = property.getProperty(StaticConst.LOGIN_URL);
			//邮件主题
			String subject = "新用户注册通知";
			//收件人地址
			String toMail = map.get("email1").toString();
			//使用模板发送邮件
			String str = MailTemplateUtils.getContentByTemplate(StaticConst.MAIL_REGIST_CONTENT);
			//内容
			String content = PlaceholderConfigurer.formatText(str,map.get("userName").toString(),
					map.get("loginName").toString(),map.get("oriPwd").toString(),loginUrl,loginUrl);
			//发送邮件
			SimpleMailSender.sendHtmlMail(toMail, subject, content);
			
			result.setSuccess(true);
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
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		
		if(CUtils.get().mapIsNotEmpty(map)){
			map.put("userId", CUtils.get().object2String(map.get("userId")));
		}
		map.put("companyId", StaticConst.COMPANY_ID);
		return service.resetPassword(map);
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping("resetPasswordForApp")
	@ResponseBody
	public Object resetPasswordForApp(@RequestBody String userString){
		Map<String,Object> map = CUtils.get().jsonString2map(userString);
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		try {
			//验证新密码是否符合规范
			String regex = "^(?=.*[0-9])(?=.*[a-z|A-Z])((/D)*).{8,}$";
			Pattern p = Pattern.compile(regex); 
		  	Matcher m = p.matcher(CUtils.get().object2String(map.get("password")));
		  	if(m.matches()){
		  		if(CUtils.get().mapIsNotEmpty(map)){
					map.put("userId", CUtils.get().object2String(map.get("id")));
					map.put("password", PWDUtils.genernateNewPassword(CUtils.get().object2String(map.get("password"))));
				}
				map.put("companyId", StaticConst.COMPANY_ID);
		  		if(service.resetPasswordForApp(map).isSuccess()){
		  			result.setSuccess(true);
					result.setMessage("密码已修改!");
		  		}else{
		  			result.setSuccess(false);
					result.setMessage("密码修改失败!");
		  		}
		  	}else{
		  		result.setMessage("新密码必须包含字母数字，可以包含特殊字符，8位及以上!");
		  	}
		} catch (Exception e) {
			result.setMessage("服务器异常，密码修改失败!");
		}
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
	 * 判断用户是否存在
	 * @param paramString
	 * @return
	 */
	@RequestMapping("isExitUser")
	@ResponseBody
	public Object isExitUser(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(paramMap.containsKey("userName")){
			String loginName = CUtils.get().object2String(paramMap.get("userName"));
			int res = service.isExitUser(loginName);
			if(res>0){
				result.setSuccess(true);
				result.setValue(res);
				result.setMessage("登录账号存在");
			}else{
				result.setSuccess(true);
				result.setValue(res);
				result.setMessage("登录账号不存在");
			}
		}
		return result;
	}
	
	/**
	 * 传入用户ID返回用户信息
	 * @param paramString
	 * @return
	 */
	@RequestMapping(value="getUserById", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value="根据ID获取用户", notes="参数:userId - 用户ID")
	@ApiImplicitParam(name = "paramString", value = "", required = true)
	public Object getUserById(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		
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
	@RequestMapping("getUserResource")
	@ResponseBody
	public List<Map<String,Object>> getUserResource(@RequestBody Map<String, Object> params)
	{
		return service.getUserResources(params);
	}
	@RequestMapping("getResources")
	@ResponseBody
	public List<Map<String,Object>> getResources(@RequestBody Map<String, Object> params)
	{
		return service.getResources(params);
	}
	@RequestMapping("getUserScope")
	@ResponseBody
	public List<Map<String,Object>> getUserScope(@RequestBody Map<String, Object> params)
	{
		return service.getUserScope(params);
	}
	
	
	/**
	 * 获取共享列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getShareUserList")
	@ResponseBody
	public Object getShareUserList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		
		List<Map<String,Object>> info =service.getShareUserList(map);
		if(!info.isEmpty() && info.size()>0){
			result.setSuccess(true);
			result.setValue(info);
		}
		return result;
	}
	
	/**
	 * 根据用户id获取用户名和所在部门
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getCreadIdInfo")
	@ResponseBody
	public Object getCreadIdInfo(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		map.put("companyId", StaticConst.COMPANY_ID);
		List<Map<String,Object>> info =service.getCreadIdInfo(map);
		if(!info.isEmpty() && info.size()>0){
			result.setSuccess(true);
			result.setValue(info);
		}
		return result;
	}
	
	@RequestMapping(value="updatePwd",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="修改密码", notes="参数:userId - 用户ID, password - 密码")
	@ApiImplicitParam(name = "userString", value = "", required = true)
	public ResultBean updatePwd(@RequestBody String userString){
		ResultBean rtn = new ResultBean();
		try
		{
			Map<String,Object> map = CUtils.get().jsonString2map(userString);
			if(!map.containsKey("userId") || !map.containsKey("password"))
			{
				rtn.setSuccess(false);
				rtn.setMessage("必要参数丢失！");
				return rtn;
			}
			service.updatePwd(map);
			rtn.setSuccess(true);
		} catch (Exception e)
		{
			rtn.setSuccess(false);
			rtn.setMessage("修改失败");
			e.printStackTrace();
		}
		
		return rtn;
	}
	
	/**
	 * 检测用户是否绑定角色
	 * @param mapString
	 * @return
	 */
	@RequestMapping("checkBindRole")
	@ResponseBody
	public Object checkBindRole(@RequestBody String mapString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(true);
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(mapString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().mapIsNotEmpty(paramMap)){
			if(service.checkBindRole(paramMap)){
				result.setSuccess(false);
			}
		}
		return result;
	}
	
	/**
	 * 根据传入的部门ID返回对应的所有投资经理
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getTZJLByDepId")
	@ResponseBody
	public Object getTZJLByDepId(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
			
		Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
		List<Map<String, Object>> list = service.getTZJLByDepId(paramMap);
		if(list!=null){
			result.setSuccess(true);
			result.setValue(list);
		}
		return result;
	}
	

}
