package com.galaxy.authority.business.user.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
/**
 * 所有人
 * @author wangsong
 *
 */
@Component
public class EveryoneScopeHandler extends AbstractScopeHandler implements ScopeHandler {

	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 2 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
		return new ArrayList<Integer>();
	}

}
