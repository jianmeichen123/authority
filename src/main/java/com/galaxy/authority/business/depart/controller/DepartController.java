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
	private List<Map<String,Object>> departList = null;
	
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
				//System.out.println(paramJson.getLong("parentId"));
				paramMap.put("parentId", paramJson.getLong("parentId"));
			}
		}
		
		List<Map<String,Object>> dataList = service.getDepartList(paramMap);
		if(dataList!=null && dataList.size()>0){
			result.setSuccess(true);
		}
		result.setValue(dataList);
		
		System.out.println(CUtils.get().List2JSONString(dataList));
		
		return result;
	}
	
	/**
	 * 显示部门树型结构
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
		departList = service.getDepartTreeList(paramMap);
		createDepartChild(departList);
		return departList;
	}
	
	/**
	 * 创建部门
	 * @param paramString
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object saveDepart(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		result.setMessage("添加部门失败");
		
		JSONObject paramJson = CUtils.get().object2JSONObject(paramString);
		if(paramJson!=null){
			DepartBean departBean = new DepartBean();
			departBean.setDepName(paramJson.getString("departName"));
			departBean.setParentId(-1);
			if(paramJson.has("departId") && CUtils.get().stringIsNotEmpty(paramJson.getString("departId"))){
				departBean.setParentId(CUtils.get().object2Long(paramJson.getString("departId"),-1L));
			}
			departBean.setCompanyId(StaticConst.COMPANY_ID);
			int count = service.saveDepart(departBean);
			System.out.println(departBean.getId());
			
			if(count>0){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", departBean.getId());
				map.put("name", departBean.getDepName());
				map.put("parentId", departBean.getParentId());
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
	private void createDepartChild(List<Map<String,Object>> depList){
		for(int i=0;i<depList.size();i++){
			if(depList.get(i)!=null){
				paramMap.put("parentId", depList.get(i).get("id"));
				paramMap.put("companyId", StaticConst.COMPANY_ID);
				List<Map<String,Object>> dataList = service.getDepartTreeList(paramMap);
				if(dataList!=null && !dataList.isEmpty()){
					createDepartChild(dataList);
					depList.get(i).put("children", dataList);
				}
			}
		}
	}
	
	

}
