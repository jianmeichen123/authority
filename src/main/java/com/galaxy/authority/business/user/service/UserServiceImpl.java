package com.galaxy.authority.business.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.depart.RelDepUser;
import com.galaxy.authority.bean.position.RelPosUser;
import com.galaxy.authority.bean.user.UserBean;
import com.galaxy.authority.business.user.component.ScopeHandler;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.PWDUtils;
import com.galaxy.authority.common.PropertiesUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.common.mail.MailTemplateUtils;
import com.galaxy.authority.common.mail.PlaceholderConfigurer;
import com.galaxy.authority.common.mail.SimpleMailSender;
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
	@Autowired
	ApplicationContext context;

	@Override
	public boolean saveUser(Map<String, Object> map) {
		boolean flag = false;
		UserBean userBean = new UserBean();
		String oriPwd = CUtils.get().object2String(map.get("oriPwd"));
		userBean.setOriginPassword(oriPwd);
		userBean.setPassword(PWDUtils.genernateNewPassword(oriPwd));
		userBean.setLoginName(CUtils.get().object2String(map.get("loginName")));
		userBean.setUserName(CUtils.get().object2String(map.get("userName")));
		userBean.setMobilePhone(CUtils.get().object2String(map.get("mobilePhone")));
		userBean.setEmail1(CUtils.get().object2String(map.get("email1")));
		userBean.setCompanyId(StaticConst.COMPANY_ID);
		userBean.setSex(CUtils.get().object2Integer(map.get("sex")));
		userBean.setIsAdmin(CUtils.get().object2Integer(map.get("isAdmin")));
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
	public ResultBean resetPassword(Map<String, Object> paramMap) {
		ResultBean result = ResultBean.instance();
		int retValue = 0;
		
		//获取用户信息by用户id
		Map<String, Object> user=dao.getUserById(paramMap);
		if(!"".equals(user.get("oriPass")) && user.get("oriPass")!=null){
			paramMap.put("password", PWDUtils.genernateNewPassword(user.get("oriPass").toString()));
		}
		//重置密码业务处理
		retValue = dao.resetPassword(paramMap);
		
		//登陆地址
		Properties property = PropertiesUtils.getProperties(StaticConst.MAIL_CONFIG_FILE);
		String loginUrl = property.getProperty(StaticConst.LOGIN_URL);
		//邮件主题
		String subject = "重置密码通知";
		//收件人地址
		String toMail = user.get("mail1").toString();
		//使用模板发送邮件
		String str = MailTemplateUtils.getContentByTemplate(StaticConst.MAIL_RESTPWD_CONTENT);
		//内容
		String content = PlaceholderConfigurer.formatText(str, user.get("userName").toString(),
				user.get("loginName").toString(),user.get("oriPass").toString(),loginUrl,loginUrl);
		
		if (retValue < 1) {
			result.setSuccess(false);
		}else{
			//发送邮件
			SimpleMailSender.sendHtmlMail(toMail, subject, content);
			result.setSuccess(true);
		}
		return result;
	}
	
	public List<Map<String,Object>> getUserResources(Map<String,Object> paramMap)
	{
		return dao.getUserResources(paramMap);
	}
	
	private List<ScopeHandler> handlers;
	public List<Map<String,Object>> getUserScope(Map<String,Object> paramMap)
	{
		List<Map<String,Object>> list = dao.getUserScope(paramMap);
		if(list != null && list.size() >0)
		{
			for(Map<String,Object> item : list)
			{
				if(handlers != null)
				{
					for(ScopeHandler handler : handlers)
					{
						if(handler.support(item))
						{
							List<Integer> userIds = handler.handle(item);
							item.put("userIds", userIds);
							break;
						}
					}
				}
			}
		}
		
		return list;
	}
	/**
	 * 获取用户相应资源的数据权限
	 * @param paramMap resourceMark:{uid1,uid2......}
	 * @return
	 */
	public Map<String,Set<Integer>> getUserResourceScope(Map<String,Object> paramMap)
	{
		Map<String,Set<Integer>> map = new HashMap<>();
		List<Map<String,Object>> list = dao.getUserScope(paramMap);
		if(list == null || list.size() ==0 || handlers == null)
		{
			return map;
		}
		//如果是所有人，取最大范围
		Set<String> everyOneSet = new HashSet<>();
		for(Map<String,Object> item : list)
		{
			for(ScopeHandler handler : handlers)
			{
				if(handler.support(item))
				{
					List<Integer> userIds = handler.handle(item);
					String resourceMark = item.get("resourceMark")+"";
					Integer spId = (Integer)item.get("spId");
					//所有人
					if(everyOneSet.contains(resourceMark))
					{
						map.remove(resourceMark);
						break;
					}else if(spId != null && spId.intValue()==2)
					{
						everyOneSet.add(resourceMark);
						map.remove(resourceMark);
						break;
					}
					if(userIds == null || userIds.size() ==0)
					{
						break;
					}
					Set<Integer> ids = null;
					if(!map.containsKey(resourceMark))
					{
						ids = new HashSet<>();
						map.put(resourceMark, ids);
					}
					else
					{
						ids = map.get(resourceMark);
					}
					ids.addAll(userIds);
					break;
				}
			}
		}
		return map;
	}
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception 
	{
		Map<String, ScopeHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ScopeHandler.class, true, false);
		 if(map != null)
		 {
			 handlers = new ArrayList<ScopeHandler>(map.values());
		 }
		
	}

	/**
	 * 获取共享用户list
	 */
	@Override
	public List<Map<String, Object>> getShareUserList(Map<String, Object> map) {
		String userIds="";
		List<Long> list = new ArrayList<Long>();
		
		Map<String, Set<Integer>> info =this.getUserResourceScope(map);
		//获取数据范围下的所有相关用户id并做处理
		if(!info.isEmpty()&&info.size()>0){
			//for(Map<String,Object> info:scope){
				if(info.containsKey("callOn_shareUser")){
					/*if(Integer.valueOf(info.get("spId").toString())==2){
						list = dao.getUserIdList();
					}else{*/
						userIds = info.get("callOn_shareUser").toString();
						userIds = userIds.replace(" ", "");
						if(userIds.startsWith("[")){
							userIds = userIds.substring(1);
						}
						if(userIds.endsWith("]")){
							userIds = userIds.substring(0,userIds.length()-1);
						}
					//}
				}
			//}
		}
		//分割放在list
		if(!userIds.isEmpty()){
			String[] userId=userIds.split(",");
			if(userId!=null && userId.length>0){
				for(int i=0;i<userId.length;i++){
					if(!map.get("userId").equals(userId[i])){
						list.add(Long.valueOf(userId[i]));
					}
				}
			}
		}
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> userMap =new HashMap<String, Object>();
		userMap.put("toUid", map.get("userId"));
		userMap.put("toUname", "我的拜访");
		dataList.add(userMap);
		if(!list.isEmpty()){
			map.put("userIdList", list);
			List<Map<String,Object>>  res = dao.getUserNameList(map);
			dataList.addAll(res);
		}
		
		return dataList;
	}

	/**
	 * 根据用户id获取用户名和所在部门
	 */
	@Override
	public List<Map<String, Object>> getCreadIdInfo(Map<String, Object> map) {
		
		List<Map<String,Object>>  res = dao.getCreadIdInfo(map);
		return res;
	}

	/**
	 * 判断登录账号是否已经存在
	 */
	@Override
	public int isExitUser(String loginName) {
		int count = dao.isExitUser(loginName);
		return count;
	}
	
	@Transactional
	public int updatePwd(Map<String,Object> query) {
        if (query!=null && query.get("userId")!= null && query.get("password")!=null) {
        	// 加密
    		query.put("password",PWDUtils.genernateNewPassword(query.get("password").toString()));
    		dao.updatePwd(query);
    		return 1;
        } else {
        	return 0;
        }
		
	}

	@Override
	public List<Map<String, Object>> getResources(Map<String, Object> params)
	{
		return dao.getResources(params);
	}

	//修改密码
	@Override
	public ResultBean resetPasswordForApp(Map<String, Object> paramMap) {
		ResultBean result = ResultBean.instance();
		int retValue = 0;
		
		//获取用户信息by用户id
		Map<String, Object> user=dao.getUserById(paramMap);
		//修改密码业务处理
		retValue = dao.resetPassword(paramMap);
		
		//登陆地址
		Properties property = PropertiesUtils.getProperties(StaticConst.MAIL_CONFIG_FILE);
		String loginUrl = property.getProperty(StaticConst.LOGIN_URL);
		//邮件主题
		String subject = "重置密码通知";
		//收件人地址
		String toMail = user.get("mail1").toString();
		//使用模板发送邮件
		String str = MailTemplateUtils.getContentByTemplate(StaticConst.MAIL_RESTPWD_CONTENT);
		//内容
		String content = PlaceholderConfigurer.formatText(str, user.get("userName").toString(),
				user.get("loginName").toString(),user.get("oriPass").toString(),loginUrl,loginUrl);
		
		if (retValue < 1) {
			result.setSuccess(false);
		}else{
			//发送邮件
			SimpleMailSender.sendHtmlMail(toMail, subject, content);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 检测用户是否绑定角色
	 */
	@Override
	public boolean checkBindRole(Map<String, Object> paramMap) {
		int count = dao.checkBindRole(paramMap);
		return count>0;
	}
	

}
