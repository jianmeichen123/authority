package com.galaxy.authority.common.webconfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.galaxy.authority.common.webconfig.servlet.OneServlet;
import com.galaxy.authority.common.webconfig.servlet.SecondServlet;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	@Bean
	public ServletRegistrationBean getOneServlet(){
		OneServlet demoServlet=new OneServlet();
		ServletRegistrationBean registrationBean=new ServletRegistrationBean();
		registrationBean.setServlet(demoServlet);
		List<String> urlMappings=new ArrayList<String>();
		urlMappings.add("/one");				////访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
	
	@Bean
	public ServletRegistrationBean getSecondServlet(){
		SecondServlet demoServlet=new SecondServlet();
		ServletRegistrationBean registrationBean=new ServletRegistrationBean();
		registrationBean.setServlet(demoServlet);
		List<String> urlMappings=new ArrayList<String>();
		urlMappings.add("/two");				////访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}

}
