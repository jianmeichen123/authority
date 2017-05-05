package com.galaxy.authority.business.user.component;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
/**
 * 本人
 * @author wangsong
 *
 */
@Component
public class SelfScopeHandler extends AbstractScopeHandler implements ScopeHandler  {

	@Override
	public boolean support(Object data) {
		Integer spId = getVal(data,"spId");
		return 1 == spId;
	}

	@Override
	public List<Integer> handle(Object data) {
		Integer userId = getVal(data,"userId");
		return Arrays.asList(userId);
	}

}
