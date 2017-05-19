package com.galaxy.authority.dao.position;

import java.util.List;
import java.util.Map;

import com.galaxy.authority.bean.position.PositionBean;

public interface IPositionDao {
	List<Map<String,Object>> getPositionList(Map<String,Object> paramMap);
	List<Map<String,Object>> getPositionComboxList(Map<String,Object> paramMap);
	int getPositionListCount(Map<String,Object> paramMap);
	int savePosition(PositionBean bean);
	int checkPositionDel(Map<String,Object> paramMap);
	int outtagePos(Map<String,Object> paramMap);
	int delPos(Map<String,Object> paramMap);
	int updatePos(PositionBean bean);
	//判断职位是否存在
	int isExitPosition(String posName);
}
