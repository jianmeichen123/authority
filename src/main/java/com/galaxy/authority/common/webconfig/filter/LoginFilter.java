package com.galaxy.authority.common.webconfig.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.common.redisconfig.RedisCacheImpl;

public class LoginFilter implements Filter{
	private Logger log = LoggerFactory.getLogger(LoginFilter.class);


	private static String[] excludes;
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest)req;
		
		/*@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		String uri = request.getRequestURI();
		if(uri != null && excludes != null && excludes.length > 0)
		{
			for(String exclude : excludes)
			{
				if(uri.indexOf(exclude)>-1)
				{
					filterChain.doFilter(req, resp);
					return;
				}
			}
		}
		
		boolean flag = false;
		String sessionId = request.getHeader(StaticConst.CONST_SESSION_ID_KEY);
		log.error(request.getRequestURI()+"================"+sessionId);
		if(request.getRequestURI().indexOf(StaticConst.FILTER_WHITE_LOGIN)>0){
			filterChain.doFilter(req, resp);
		}else{
			if(CUtils.get().stringIsNotEmpty(sessionId) && !"NULL".equals(sessionId.toUpperCase()) && cache.hasKey(sessionId)){
				if(CUtils.get().stringIsNotEmpty(cache.get(sessionId))){
					flag = true;
				}
			}
			
			if(flag){
				filterChain.doFilter(req, resp);
			}else{
				resp.setContentType("text/html;charset=utf-8");
				ResultBean result = ResultBean.instance();
				result.setSuccess(false);
				result.setMessage("user is not login,or post due");
				result.setErrorCode("3");
				
				CUtils.get().outputJson(resp,result);
			}
		}*/
			filterChain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String excludeStr = config.getInitParameter("excludes");
		if(excludeStr != null)
		{
			excludes = excludeStr.split(",");
		}
		
	}


	
	

}
