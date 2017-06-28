package com.galaxy.authority.business.depart.controller;

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

import com.galaxy.authority.InitService;
import com.galaxy.authority.bean.ResultBean;
import com.galaxy.authority.bean.depart.DepartBean;
import com.galaxy.authority.business.depart.service.IDepartService;
import com.galaxy.authority.common.CUtils;
import com.galaxy.authority.common.DateUtil;
import com.galaxy.authority.common.StaticConst;

@Controller
@RequestMapping("/depart")
public class DepartController {
	
	@Autowired
	private IDepartService service;
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	/**
	 * 部门列表
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDeaprtList")
	@ResponseBody
	public Object showDepartList(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
	
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("companyId", StaticConst.COMPANY_ID);
		paramMap.put("parentId", 0);
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
	 * 获得事业线
	 */
	@RequestMapping("getCareerLineList")
	@ResponseBody
	public Object getCareerLineList(){
		return service.getCareerLineList();
	}
	
	/**
	 * 显示部门树型结构-ztree
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDepartTree")
	@ResponseBody
	public Object showDepartTree(@RequestBody String paramString){
		paramMap.put("parentId", 0);
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
	
	/**
	 * 下拉树型控件-树型列表数据
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDepartComboxTree")
	@ResponseBody
	public Object showDepartComboxTree(@RequestBody String paramString){
		paramMap.put("parentId", 0);
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
			//重名判断
			int count = service.isExitDepartment(bean.getDepName());
			if(bean.getId()>0){
				if(count>0&&!bean.getDepName().equals(bean.getOldDepName())){
					result.setSuccess(false);
					result.setMessage("部门已经存在");
				}else{
					if(service.updateDepart(bean)){
						result.setSuccess(true);
						result.setValue("update");
					}
				}
			}else{
				//保存
				if(count<1){
					if(service.saveDepart(bean)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("id", bean.getId());
						map.put("name", bean.getDepName());
						map.put("parentId", bean.getParentId());
						result.setValue(map);
						result.setSuccess(true);
					}
				}else{
					result.setSuccess(false);
					result.setMessage("部门已经存在");
				}
			}
			InitService.get().initDepartList(StaticConst.COMPANY_ID);
		}
		return result;
	}
	
	/**
	 * 验证部门是否可删除
	 */
	@RequestMapping("checkDel")
	@ResponseBody
	public Object checkDelDepart(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		if(CUtils.get().stringIsNotEmpty(paramString)){
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("companyId", StaticConst.COMPANY_ID);
			result.setSuccess(!service.getDepUserCount(paramMap));
		}
		return result;
	}
	
	/**
	 * 删除部门
	 */
	@RequestMapping("delDepartment")
	@ResponseBody
	public Object delDepartment(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		if(CUtils.get().stringIsNotEmpty(paramString)){
			Map<String,Object> paramMap = CUtils.get().jsonString2map(paramString);
			paramMap.put("companyId", StaticConst.COMPANY_ID);
			paramMap.put("updateTime", DateUtil.getMillis(new Date()));
			result.setSuccess(service.delDepartment(paramMap));
			
			InitService.get().initDepartList(StaticConst.COMPANY_ID);
		}
		return result;
	}
	
	/*-----------------对外服务接口----------------------*/
	@RequestMapping("getLeafDepartList")
	@ResponseBody
	public Object getLeafDepartList(){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		
		if(CUtils.get().listIsNotEmpty(StaticConst.depList)){
			result.setSuccess(true);
			result.setValue(StaticConst.depList);
		}
		
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("companyId", StaticConst.COMPANY_ID);
		
//		http://10.8.232.78:8888/authority_service/role/getRoleList
//		http://10.8.232.78:8888/authority_service/depart/getLeafDepartList
//		
//		List<Map<String,Object>> dataList = service.getLeafDepartList(paramMap);
//		if(CUtils.get().listIsNotEmpty(dataList)){
//			result.setSuccess(true);
//			result.setValue(dataList);
//		}
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
	
	/**
	 * 根据部门名称获取部门id
	 * @param paramString
	 * @return
	 */
	@RequestMapping("getDeptIdByDeptName")
	@ResponseBody
	public Object getDeptIdByDeptName(@RequestBody String paramString){
		ResultBean result = ResultBean.instance();
		result.setSuccess(false);
		Map<String,Object> map = CUtils.get().jsonString2map(paramString);
		map.put("companyId", StaticConst.COMPANY_ID);
		List<Map<String,Object>> info =service.getDeptIdByDeptName(map);
		if(!info.isEmpty() && info.size()>0){
			result.setSuccess(true);
			result.setValue(info);
		}
		return result;
	}
	
	

}
