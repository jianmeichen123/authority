package com.galaxy.authority.business.user.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.depart.RelDepUser;
import com.galaxy.authority.bean.position.RelPosUser;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.PWDUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.dao.position.IRelPosUserDao;
import com.galaxy.authority.dao.user.IRelDepUserDao;
import com.galaxy.authority.dao.user.IUserDao;

@Repository
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao dao;
	@Autowired
	private IRelDepUserDao rDao;
	@Autowired
	private IRelPosUserDao pDao;

	@Override
	public boolean saveUser(Map<String, Object> map) {
		boolean flag = false;
		UserBean userBean = new UserBean();
		//生成原密码
		String oriPwd = PWDUtils.genRandomNum(6);
		userBean.setOriginPassword(oriPwd);
		userBean.setPassword(PWDUtils.genernateNewPassword(oriPwd));
		userBean.setLoginName(CUtils.get().object2String(map.get("loginName")));
		userBean.setUserName(CUtils.get().object2String(map.get("userName")));
		userBean.setMobilePhone(CUtils.get().object2String(map.get("mobilePhone")));
		userBean.setEmail1(CUtils.get().object2String(map.get("email1")));
		userBean.setCompanyId(StaticConst.COMPANY_ID);
		userBean.setSex(CUtils.get().object2Integer(map.get("sex")));
		userBean.setEmployNo(CUtils.get().object2String(map.get("employNo")));
		userBean.setTelphone(CUtils.get().object2String(map.get("telPhone")));
		userBean.setAddress(CUtils.get().object2String(map.get("address")));
		int count = dao.saveUser(userBean);
		
		if(count>0){
			//保存部门关联表
			RelDepUser rdu = new RelDepUser();
			rdu.setCompanyId(StaticConst.COMPANY_ID);
			rdu.setDepId(CUtils.get().object2Long(map.get("departId")));
			rdu.setUserId(userBean.getId());
			int cc = rDao.saveRelDepUser(rdu);
			
			//保存职位关联表
			RelPosUser rpu = new RelPosUser();
			rpu.setCompanyId(StaticConst.COMPANY_ID);
			rpu.setPosId(CUtils.get().object2Long(map.get("positionId")));
			rpu.setUserId(userBean.getId());
			int dd = pDao.saveRelPosUser(rpu);
			
			if(cc>0 && dd>0){
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public boolean updateUser(Map<String, Object> paramMap) {
		boolean flag = false;
		//删除职位关联表相关数据
		int cc = rDao.delRelDepUser(paramMap);
		
		//删除部门关联表相关数据
		int dd = pDao.delRelPosUser(paramMap);
		
		if(cc>0 && dd>0){
			long userId = CUtils.get().object2Long(paramMap.get("userId"));
			
			//保存部门关联表
			RelDepUser rdu = new RelDepUser();
			rdu.setCompanyId(StaticConst.COMPANY_ID);
			rdu.setDepId(CUtils.get().object2Long(paramMap.get("departId")));
			rdu.setUserId(userId);
			rDao.saveRelDepUser(rdu);
			
			//保存职位关联表
			RelPosUser rpu = new RelPosUser();
			rpu.setCompanyId(StaticConst.COMPANY_ID);
			rpu.setPosId(CUtils.get().object2Long(paramMap.get("positionId")));
			rpu.setUserId(userId);
			pDao.saveRelPosUser(rpu);
			
			flag = dao.updateUser(paramMap)>0;
		}
		return flag;
	}

	@Override
	public Page<Map<String, Object>> getUserList(Map<String, Object> paramMap) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>();
		
		List<Map<String,Object>> dataList = dao.getUserList(paramMap);
		int count = dao.getUserListCount(paramMap);
		page.setResultCount(count);
		if(dataList!=null){
			page.setMapList(dataList);
		}
		return page;
	}
	
	@Override
	public List<Map<String, Object>> getUserByIds(Map<String, Object> paramMap) {
		List<Map<String,Object>> dataList = dao.getUserByIds(paramMap);
		return dataList;
	}

	@Override
	public List<Map<String, Object>> getUsersByDepId(Map<String, Object> paramMap) {
		return dao.getUsersByDepId(paramMap);
	}

	@Override
	public Map<String, Object> getUserById(Map<String, Object> paramMap) {
		return dao.getUserById(paramMap);
	}

	@Override
	public List<Map<String,Object>> findUserByName(Map<String, Object> paramMap) {
		List<Map<String,Object>> dataList = dao.findUserByNameTdj(paramMap);
		return dataList;
	}
	
	@Override
	public List<Map<String, Object>> getUsersByKey(Map<String, Object> paramMap) {
		return dao.getUsersByKey(paramMap);
	}

	@Override
	public boolean outtageUser(Map<String, Object> paramMap) {
		return dao.outtageUser(paramMap)>0;
	}

	@Override
	public boolean deleteUser(Map<String, Object> paramMap) {
		return dao.deleteUser(paramMap)>0;
	}

	@Override
	public boolean editUser(UserBean bean) {
		return dao.editUser(bean)>0;
	}

	@Override
	public boolean resetPassword(Map<String, Object> paramMap) {
		//获取用户信息by用户id
		Map<String, Object> user=dao.getUserById(paramMap);
		if(!"".equals(user.get("oriPass")) && user.get("oriPass")!=null){
			paramMap.put("password", PWDUtils.genernateNewPassword(user.get("oriPass").toString()));
		}
		
		return dao.resetPassword(paramMap)>0;
	}
	
	

}
