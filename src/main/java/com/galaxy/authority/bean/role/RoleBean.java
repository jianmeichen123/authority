package com.galaxy.authority.bean.role;

import java.util.Date;

import com.galaxy.authority.common.DateUtil;

public class RoleBean {
	private long id;				//主键id
	private String roleCode;		//角色code
	private String roleName;		//角色名称
	private String roleDemo;		//角色描述
	private int isDel;				//删除标志
	private int isOuttage;			//停用标志
	private long createTime;		//创建时间
	private long createId;			//创建人
	private long updateTime;		//更新时间
	private long updateId;			//更新人
	private long companyId;			//租户ID
	
	private String oldRoleName;		//修改之前角色名称
	
	public RoleBean(){
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
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDemo() {
		return roleDemo;
	}
	public void setRoleDemo(String roleDemo) {
		this.roleDemo = roleDemo;
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

	public String getOldRoleName() {
		return oldRoleName;
	}

	public void setOldRoleName(String oldRoleName) {
		this.oldRoleName = oldRoleName;
	}
}
