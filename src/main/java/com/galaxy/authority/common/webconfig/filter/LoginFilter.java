package com.galaxy.authority.common.webconfig.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;
import com.galaxy.authority.common.redisconfig.RedisCacheImpl;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest)req;
		
		@SuppressWarnings("unchecked")
		RedisCacheImpl<String,Object> cache = (RedisCacheImpl<String,Object>)StaticConst.ctx.getBean("cache");
		
		boolean flag = false;
		String sessionId = request.getHeader(StaticConst.CONST_SESSION_ID_KEY);
		
		if(request.getRequestURI().indexOf(StaticConst.FILTER_WHITE_LOGIN)>0){
			filterChain.doFilter(req, resp);
		}else{
			if(CUtils.get().stringIsNotEmpty(sessionId) && cache.hasKey(sessionId)){
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
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}


	
	

}
