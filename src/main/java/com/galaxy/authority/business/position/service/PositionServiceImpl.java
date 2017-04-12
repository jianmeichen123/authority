package com.galaxy.authority.business.position.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.position.PositionBean;
import com.galaxy.authority.dao.position.IPositionDao;

@Repository
public class PositionServiceImpl implements IPositionService{
	@Autowired
	private IPositionDao dao;

	@Override
	public Page<PositionBean> getPositionList(Map<String, Object> paramMap) {
		Page<PositionBean> page = new Page<PositionBean>();
		int count = dao.getPositionListCount(paramMap);
		page.setResultCount(count);
		page.setMapList(dao.getPositionList(paramMap));
		return page;
	}

	@Override
	public boolean savePosition(PositionBean bean) {
		int count = dao.savePosition(bean);
		return (count>0);
	}

	@Override
	public List<Map<String, Object>> getPositionComboxList(Map<String, Object> paramMap) {
		return dao.getPositionComboxList(paramMap);
	}

	@Override
	public boolean checkPositionDel(Map<String, Object> paramMap) {
		int count = dao.checkPositionDel(paramMap);
		return count>0;
	}

	@Override
	public boolean outtagePos(Map<String, Object> paramMap) {
		int count = dao.outtagePos(paramMap);
		return count>0;
	}

	@Override
	public boolean delPos(Map<String, Object> paramMap) {
		int count = dao.delPos(paramMap);
		return count>0;
	}

	@Override
	public boolean updatePos(PositionBean bean) {
		int count = dao.updatePos(bean);
		return count>0;
	}
	
	
	
	
	
}