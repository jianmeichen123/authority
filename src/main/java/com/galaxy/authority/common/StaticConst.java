package com.galaxy.authority.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class StaticConst {
	// 注册时候与ssoserver通讯时加密解密的密钥
	public final static String SECRET_KEY = "2B4eWxHdTaWPRGnN";
		
	public static final int COMPANY_ID = 1;
	public static final int THREAD_POOL_SIZE = 10;
	public static ApplicationContext ctx;
	
	public static List<Map<String,Object>> depList = new ArrayList<Map<String,Object>>();
	public static Map<String,List<Map<String,Object>>> userMap = new HashMap<String,List<Map<String,Object>>>();

}
