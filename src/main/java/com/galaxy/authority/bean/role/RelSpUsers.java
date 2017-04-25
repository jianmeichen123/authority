package com.galaxy.authority.bean.role;

import java.util.Date;

import com.galaxy.authority.common.DateUtil;

public class RelSpUsers {
	private long id;				//主键id
	private int relRoleScopeId;		//角色资源与数据范围关联表的关联id
	private String otherId;			//用户id或部门id
	private int isDep;				//0：部门，1：用户
	private int isDel;				//删除标志
	private int isOuttage;			//停用标志
	private long createTime;		//创建时间
	private long createId;			//创建人
	private long updateTime;		//更新时间
	private long updateId;			//更新人
	private long companyId;			//租户ID
	
	public RelSpUsers(){
		this.isDel = 0;
		this.isOuttage = 0;
		this.createTime = DateUtil.getMillis(new Date());
		this.updateTime = DateUtil.getMillis(new Date());
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRelRoleScopeId() {
		return relRoleScopeId;
	}
	public void setRelRoleScopeId(int relRoleScopeId) {
		this.relRoleScopeId = relRoleScopeId;
	}
	public int getIsDep() {
		return isDep;
	}
	public void setIsDep(int isDep) {
		this.isDep = isDep;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getIsOuttage() {
		return isOuttage;
	}
	public void setIsOuttage(int isOuttage) {
		this.isOuttage = isOuttage;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getCreateId() {
		return createId;
	}
	public void setCreateId(long createId) {
		this.createId = createId;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(long updateId) {
		this.updateId = updateId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getOtherId() {
		return otherId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}
}
