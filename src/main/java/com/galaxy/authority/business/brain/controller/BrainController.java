package com.galaxy.authority.business.brain.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.business.brain.service.IBrainService;

@Controller
@RequestMapping("/brain")
public class BrainController {
	private Logger log = LoggerFactory.getLogger(BrainController.class);
	@Autowired
	private IBrainService service;

	/**
	 * 根据 用户id 查询返回部门id
	 * @param userId
	 * @return
	 */
	@RequestMapping("selectDepIdByUserId")
	@ResponseBody
	public Object selectDepIdByUserId(@RequestBody String userId)
	{
		Long id = 0L;
		Map<String,Object> paramMap = new HashMap<>();
		try {
			paramMap.put("userId", userId);
			id = service.selectDepIdByUserId(paramMap);
		} catch (Exception e) {
			log.error(BrainController.class.getName() + "selectDepIdByUserId",e);
		}
		return id;
	}
}
