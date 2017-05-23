package com.galaxy.authority.business.position.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.position.PositionBean;
import com.galaxy.authority.business.position.service.IPositionService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/position")
public class PositionController {
	@Autowired
	private IPositionService service;
	
	@RequestMapping("getPositionList")
	@ResponseBody
	public Object getPositionList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			if(CUtils.get().mapIsNotEmpty(paramMap)){
				int pageNo = CUtils.get().object2Integer(paramMap.get("pageNo"));
				int pageSize = CUtils.get().object2Integer(paramMap.get("pageSize"));
				int startNo = (pageNo-1)*pageSize;
				paramMap.put("startNo", startNo<0?0:startNo);
				paramMap.put("companyId", StaticConst.COMPANY_ID);
				
				Page<PositionBean> page = service.getPositionList(paramMap);
				if(page!=null && page.getMapList()!=null){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("total", page.getResultCount());
					map.put("rows", page.getMapList());
					result.setSuccess(true);
					result.setValue(map);
				}
			}
		}
		return result;
	}
	
	@RequestMapping("savePosition")
	@ResponseBody
	public Object savePosition(@RequestBody String beanString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		String oldPosName = "";
		Map<String,Object> map = CUtils.get().jsonString2map(beanString);
		PositionBean bean = new PositionBean();
		if(map.get("posName")!=null){
			bean.setPosName(CUtils.get().object2String(map.get("posName")));
		}
		if(map.get("oldPosName")!=null){
			oldPosName =CUtils.get().object2String(map.get("oldPosName"));
		}
		if(map.get("id")!=null){
			bean.setId(CUtils.get().object2Long(map.get("id")));
		}
		if(bean!=null){
			bean.setCompanyId(StaticConst.COMPANY_ID);
			//重名判断
			int count = service.isExitPosition(bean.getPosName());
			if(bean.getId()!=0){
				if(count>0&&!bean.getPosName().equals(oldPosName)){
					result.setSuccess(false);
					result.setMessage("职位已经存在");
				}else{
					bean.setUpdateTime(DateUtil.getMillis(new Date()));
					result.setSuccess(service.updatePos(bean));
				}
			}else{
				//保存
				if(count<1){
					result.setSuccess(service.savePosition(bean));
				}else{
					result.setSuccess(false);
					result.setMessage("职位已经存在");
				}
				
			}
		}
		return result;
	}
	
	@RequestMapping("getPositionComboxList")
	@ResponseBody
	public Object getPositionComboxList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("isOuttage")){
				paramMap.put("isOuttage", paramJson.getLong("isOuttage"));
			}
		}
		List<Map<String,Object>> dataList = service.getPositionComboxList(paramMap);
		if(CUtils.get().listIsNotEmpty(dataList)){
			result.setSuccess(true);
			result.setValue(dataList);
		}
		
		return result;
	}
	
	/**
	 * 判断是否能被禁用或删除
	 */
	@RequestMapping("checkPosition")
	@ResponseBody
	public Object checkPosition(@RequestBody String mapString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(true);
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(mapString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().mapIsNotEmpty(paramMap)){
			if(service.checkPositionDel(paramMap)){
				result.setSuccess(false);
			}
		}
		return result;
	}
	
	/**
	 * 禁用或删除
	 */
	@RequestMapping("delOrOuttage")
	@ResponseBody
	public Object delOrOuttage(@RequestBody String mapString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		Map<String,Object> paramMap = CUtils.get().jsonString2map(mapString);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		if(CUtils.get().mapIsNotEmpty(paramMap)){
			String state = CUtils.get().object2String(paramMap.get("state"));
			if("outtage".equals(state)){
				if(service.outtagePos(paramMap)){
					result.setSuccess(true);
				}
			}else if("delete".equals(state)){
				if(service.delPos(paramMap)){
					result.setSuccess(true);
				}
			}
		}
		return result;
	}
	
	
	
}
