package com.galaxy.authority.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxy.authority.InitService;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/setting")
public class SettingController {
	
	@RequestMapping("cleancache")
	public Object cleanCache(){
		ResultBean result = ResultBean.instance();
		result.setSuccess(true);
		
		InitService.get().initDepartList(StaticConst.COMPANY_ID);
		InitService.get().initdepartUser(StaticConst.COMPANY_ID);
		
		return result;
	}

}
