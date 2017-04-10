package com.galaxy.authority.bean;

import java.util.List;
import java.util.Map;

public class Page<T> {
	private int resultCount;
	private List<T> beanList;
	private List<Map<String,Object>> mapList;
	
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	public List<Map<String, Object>> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}
}
