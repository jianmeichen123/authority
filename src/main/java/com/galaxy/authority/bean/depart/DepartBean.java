package com.galaxy.authority.bean.depart;

import java.util.Date;

import com.galaxy.authority.common.DateUtil;

public class DepartBean {
	private long id;
	private String depCode = "";
	private String depName = "";
	private long parentId;
	private int indexNo;
	private int isShow;
	private long createTime;
	private long createId;
	private long updateTime;
	private long updateId;
	private int isDel;
	private int isOuttage;
	private long depManager;
	private int companyId;
	private int isCareerLine;			//是否投资事业线
	private String oldDepName = "";		//修改之前的部门名称
	
	public DepartBean(){
		this.createTime = DateUtil.getMillis(new Date());
		this.updateTime = DateUtil.getMillis(new Date());
		this.isShow = 0;
		this.isDel = 0;
		this.isOuttage = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDepManager() {
		return depManager;
	}

	public void setDepManager(long depManager) {
		this.depManager = depManager;
	}

	public String getOldDepName() {
		return oldDepName;
	}

	public void setOldDepName(String oldDepName) {
		this.oldDepName = oldDepName;
	}

	public int getIsCareerLine() {
		return isCareerLine;
	}

	public void setIsCareerLine(int isCareerLine) {
		this.isCareerLine = isCareerLine;
	}
	
	
}
