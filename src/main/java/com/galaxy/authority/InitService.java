package com.galaxy.authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.galaxy.authority.business.depart.service.IDepartService;
import com.galaxy.authority.business.user.service.IUserService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;

public class InitService {
	private static InitService initService = null;
	
	public static synchronized InitService get(){
		if(initService==null){
			initService = new InitService(); 
		}
		return initService;
	}
	
	/**
	 * 系统初始化的时候，直接将部门列表放入内存
	 */
	public void initDepartList(long companyId){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", companyId);
		
		IDepartService depService = StaticConst.ctx.getBean(IDepartService.class);
		
		
		List<Map<String,Object>> dataList = depService.getLeafDepartList(paramMap);
		
		if(CUtils.get().listIsNotEmpty(dataList)){
			StaticConst.depList = dataList;
		}
	}
	
	/**
	 * 初始化部门-用户表
	 */
	public void initdepartUser(long companyId){
		IUserService userService = StaticConst.ctx.getBean(IUserService.class);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", StaticConst.COMPANY_ID);
		for(Map<String,Object> map : StaticConst.depList){
			String depId = CUtils.get().object2String(map.get("depId"));
			if(CUtils.get().stringIsNotEmpty(depId)){
				param.put("depId", depId);
				List<Map<String,Object>> userList = userService.getUsersByDepId(param);
				StaticConst.userMap.put(depId, userList);
			}	
		}
	}
	
	

}
