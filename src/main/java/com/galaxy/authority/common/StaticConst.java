package com.galaxy.authority.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class StaticConst {
	
	public static final int COMPANY_ID = 1;
	public static final int THREAD_POOL_SIZE = 10;
	public static ApplicationContext ctx;
	
	public static List<Map<String,Object>> depList = new ArrayList<Map<String,Object>>();
	public static Map<String,List<Map<String,Object>>> userMap = new HashMap<String,List<Map<String,Object>>>();
	
	// 注册时候与ssoserver通讯时加密解密的密钥
	public final static String SECRET_KEY = "2B4eWxHdTaWPRGnN";
	//邮件
	public static String MAIL_TEMPLATE_FILE = "mail-template.properties";
	public static String MAIL_RESTPWD_CONTENT = "mail.authority.restPwd.content";
	public static String MAIL_REGIST_CONTENT = "mail.authority.userRegist.content";
	public static String MAIL_CONFIG_FILE = "mail.properties";
	public static String MAIL_HOST_KEY = "mail.host";
	public static String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
	public static String MAIL_ADDRESS_KEY = "mail.address";
	public static String MAIL_USERNAME_KEY = "mail.username";
	public static String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
	//登录地址
	public static String LOGIN_URL = "project.home.page.url";
	

}
