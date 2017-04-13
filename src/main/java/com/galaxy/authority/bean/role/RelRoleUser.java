package com.galaxy.authority.bean.role;

public class RelRoleUser {

	private long id;				//主键id
	private String roleId;			//角色id
	private String userId;			//用户id
	private int isDel;				//删除标志
	private int isOuttage;			//停用标志
	private long createTime;		//创建时间
	private long createId;			//创建人
	private long updateTime;		//更新时间
	private long updateId;			//更新人
	private long companyId;			//租户ID
	
	public RelRoleUser(){
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
