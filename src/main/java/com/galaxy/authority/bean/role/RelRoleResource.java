package com.galaxy.authority.bean.role;

import java.beans.Transient;
import java.util.Date;

import com.galaxy.authority.common.DateUtil;

public class RelRoleResource {
	
	private long id;				//主键id
	private int roleId;				//角色id
	private int resId;				//资源id
	private int isDel;				//删除标志
	private int isOuttage;			//停用标志
	private long createTime;		//创建时间
	private long createId;			//创建人
	private long updateTime;		//更新时间
	private long updateId;			//更新人
	private long companyId;			//租户ID
	private int tempSpId;			//临时数据范围id
	private String tempOtherStr;	//临时其他选项内容
	
	public RelRoleResource(){
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	
	@Transient
	public int getTempSpId() {
		return tempSpId;
	}

	public void setTempSpId(int tempSpId) {
		this.tempSpId = tempSpId;
	}
	@Transient
	public String getTempOtherStr() {
		return tempOtherStr;
	}

	public void setTempOtherStr(String tempOtherStr) {
		this.tempOtherStr = tempOtherStr;
	}
	

}
