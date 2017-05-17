package com.galaxy.authority.common.redisconfig;

public interface IRedisCache<K,V> {
	boolean hasKey(K key);
	void put(K key, V obj);
	V get(K key);
	
}
