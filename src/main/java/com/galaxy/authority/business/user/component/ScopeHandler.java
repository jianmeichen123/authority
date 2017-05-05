package com.galaxy.authority.business.user.component;

import java.util.List;

public interface ScopeHandler {

	boolean support(Object data);
	List<Integer> handle(Object data);
}
