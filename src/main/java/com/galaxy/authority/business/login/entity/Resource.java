package com.galaxy.authority.business.login.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Resource
{
	private Long id;
	private String resourceMark;
	private Integer spId;
	private Integer isDep;
	private Set<Integer> userIds = new HashSet<>();
	private Set<Integer> depIds = new HashSet<>();
	private Set<String> depNames = new HashSet<>();
	private List<Map<String, Object>> dept = new ArrayList<Map<String, Object>>();
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getResourceMark()
	{
		return resourceMark;
	}
	public void setResourceMark(String resourceMark)
	{
		this.resourceMark = resourceMark;
	}
	public Integer getSpId()
	{
		return spId;
	}
	public void setSpId(Integer spId)
	{
		this.spId = spId;
	}
	public Integer getIsDep()
	{
		return isDep;
	}
	public void setIsDep(Integer isDep)
	{
		this.isDep = isDep;
	}
	public Set<Integer> getUserIds()
	{
		return userIds;
	}
	public void setUserIds(Set<Integer> userIds)
	{
		this.userIds = userIds;
	}
	public Set<Integer> getDepIds()
	{
		return depIds;
	}
	public void setDepIds(Set<Integer> depIds)
	{
		this.depIds = depIds;
	}
	public Set<String> getDepNames() {
		return depNames;
	}
	public void setDepNames(Set<String> depNames) {
		this.depNames = depNames;
	}
	public List<Map<String, Object>> getDept() {
		return dept;
	}
	public void setDept(List<Map<String, Object>> dept) {
		this.dept = dept;
	}
}
