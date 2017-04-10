package com.galaxy.authority.business.user.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.depart.RelDepUser;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.user.IRelDepUserDao;
import com.galaxy.authority.dao.user.IUserDao;

@Repository
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao dao;
	@Autowired
	private IRelDepUserDao rDao;

	@Override
	public boolean saveUser(Map<String, Object> map) {
		boolean flag = false;
		UserBean userBean = new UserBean();
		userBean.setLoginName(CUtils.get().object2String(map.get("loginName")));
		userBean.setUserName(CUtils.get().object2String(map.get("userName")));
		userBean.setMobilePhone(CUtils.get().object2String(map.get("mobilePhone")));
		userBean.setEmail1(CUtils.get().object2String(map.get("email1")));
		userBean.setCompanyId(StaticConst.COMPANY_ID);
		int count = dao.saveUser(userBean);
		
		//保存关联表
		if(count>0){
			RelDepUser rdu = new RelDepUser();
			rdu.setCompanyId(StaticConst.COMPANY_ID);
			rdu.setDepId(CUtils.get().object2Long(map.get("departId")));
			rdu.setUserId(userBean.getId());
			int cc = rDao.saveRelDepUser(rdu);
			
			if(cc>0 && count>0){
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public Page<UserBean> getUserList(Map<String, Object> paramMap) {
		Page<UserBean> page = new Page<UserBean>();
		
		List<Map<String,Object>> dataList = dao.getUserList(paramMap);
		int count = dao.getUserListCount(paramMap);
		page.setResultCount(count);
		if(dataList!=null){
			page.setMapList(dataList);
		}
		return page;
	}
	
	
	
	

}
