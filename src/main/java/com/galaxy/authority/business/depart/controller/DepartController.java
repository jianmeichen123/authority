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
	
	@RequestMapping("getDepartList")
	@ResponseBody
	public Object showDepart(@RequestBody String paramString){
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
		
		
		System.out.println(CUtils.get().List2JSONString(departList));
		
		return departList;
	}
	
	/**
	 * 生成树型列表
	 * @param depList
	 */
	private void createDepartChild(List<Map<String,Object>> depList){
		for(int i=0;i<depList.size();i++){
			Map<String,Object> map = depList.get(i);
			if(map!=null){
				String parentId = CUtils.get().object2String(map.get("id"));
				paramMap.put("parentId", parentId);
				paramMap.put("companyId", StaticConst.COMPANY_ID);
				List<Map<String,Object>> dataList = service.getDepartTreeList(paramMap);
				if(dataList!=null && !dataList.isEmpty()){
					createDepartChild(dataList);
					map.put("children", dataList);
				}
			}
		}
	}
	
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
	
//	JSONObject json = null;
//	Map<String,Object> paramMap = new HashMap<String,Object>();
//	Map<String,Object> resultMap = new HashMap<String,Object>();
//	List<DepartBean> dataList = null;
//	private JSONObject createDepartJson(){
//		if(json==null){
//			paramMap.put("parentId", -1);
//			paramMap.put("companyId", StaticConst.COMPANY_ID);
//		}
//		
//		dataList = service.getDepartTreeList(paramMap);
//		if(dataList!=null && dataList.size()>0){
//			for(int i=0;i<dataList.size();i++){
//				if(dataList.get(0).getParentId()==0){
//					
//				}else{
//					
//				}
//				
//				
//			}
//		}
//		
//		
//		
//		
//		return json;
//	}
	
	

}
