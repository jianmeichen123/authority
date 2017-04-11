package com.galaxy.authority.business.depart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.depart.DepartBean;
import com.galaxy.authority.business.depart.service.IDepartService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/depart")
public class DepartController {
	@Autowired
	private IDepartService service;
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	@RequestMapping("getDeaprtList")
	@ResponseBody
	public Object showDepartList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
	
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId", -1L);
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		
		List<Map<String,Object>> dataList = service.getDepartList(paramMap);
		if(dataList!=null && dataList.size()>0){
			result.setSuccess(true);
		}
		result.setValue(dataList);
		
		return result;
	}
	
	/**
	 * 显示部门树型结构-ztree
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDepartTree")
	@ResponseBody
	public Object showDepartTree(@RequestBody String paramString){
		paramMap.put("parentId", -1);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		List<Map<String,Object>> departList = service.getDepartTreeList(paramMap);
		createDepartChild(departList,"ztree");
		return departList;
	}
	
	@RequestMapping("getDepartComboxTree")
	@ResponseBody
	public Object showDepartComboxTree(@RequestBody String paramString){
		paramMap.put("parentId", -1);
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
		if(CUtils.get().stringIsNotEmpty(paramString)){
			JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
			if(paramJson!=null && paramJson.has("parentId")){
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		List<Map<String,Object>> departList = service.getDepartForComboxTree(paramMap);
		createDepartChild(departList,"comboxTree");
		return departList;
	}
	
	/**
	 * 创建部门
	 * @param paramString
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object saveDepart(@RequestBody DepartBean bean){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		if(bean!=null){
			bean.setCompanyId(StaticConst.COMPANY_ID);
			if(service.saveDepart(bean)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", bean.getId());
				map.put("name", bean.getDepName());
				map.put("parentId", bean.getParentId());
				result.setValue(map);
				result.setSuccess(true);
				result.setMessage("添加部门成功");
			}
		}
		return result;
	}
	
	/*----------------------------------------*/
	/**
	 * 生成树型列表
	 * @param depList
	 */
	private void createDepartChild(List<Map<String,Object>> depList,String state){
		for(int i=0;i<depList.size();i++){
			if(depList.get(i)!=null){
				paramMap.put("parentId", depList.get(i).get("id"));
				paramMap.put("companyId", StaticConst.COMPANY_ID);
				List<Map<String,Object>> dataList = null;
				if("ztree".equals(state)){
					dataList = service.getDepartTreeList(paramMap);
				}else{
					dataList = service.getDepartForComboxTree(paramMap);
				}
				
				if(dataList!=null && !dataList.isEmpty()){
					createDepartChild(dataList,state);
					depList.get(i).put("children", dataList);
				}
			}
		}
	}
	
	

}
