package com.galaxy.authority.business.depart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.depart.DepartBean;
import com.galaxy.authority.business.depart.service.IDepartService;

@Controller
@RequestMapping("/depart")
public class DepartController {
	@Autowired
	private IDepartService service;
	
	@RequestMapping("show")
	@ResponseBody
	public Object showDepart(){
		DepartBean bean = service.getDepartmentById(30);
		
		
		return bean;
	}

}
