package com.galaxy.authority.business.position.service;

import java.util.List;
import java.util.Map;
import com.galaxy.authority.bean.Page;
import com.galaxy.authority.bean.position.PositionBean;

public interface IPositionService {
	Page<PositionBean> getPositionList(Map<String,Object> paramMap);
	boolean savePosition(PositionBean bean);
	List<Map<String,Object>> getPositionComboxList(Map<String,Object> paramMap);
	boolean checkPositionDel(Map<String,Object> paramMap);
	boolean outtagePos(Map<String,Object> paramMap);
	boolean delPos(Map<String,Object> paramMap);
	boolean updatePos(PositionBean bean);
	//判断职位是否存在
	int isExitPosition(String posName);
}
