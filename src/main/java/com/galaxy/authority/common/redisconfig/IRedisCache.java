package com.galaxy.authority.common.redisconfig;

import java.util.concurrent.TimeUnit;

public interface IRedisCache<K,V> {
	boolean hasKey(K key);
	void put(K key, V obj);
	void put(K key,V obj,long timeout,TimeUnit timeUnit);
	V get(K key);
	void remove(K key);
	
}
