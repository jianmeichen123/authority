package com.galaxy.authority.business.user.component;

import java.util.Map;

public class AbstractScopeHandler 
{
	@SuppressWarnings("unchecked")
	protected <T> T getVal(Object data, String key)
	{
		T result = null;
		if(data != null && data instanceof Map)
		{
			Map<String,Object> map = (Map<String,Object>)data;
			if(map.containsKey(key))
			{
				Object val = map.get(key);
				return (T)val;
			}
		}
		return result;
	}

}
