package com.galaxy.authority.common.redisconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("cache")
public class RedisCacheImpl<K,V> implements IRedisCache<K,V>{
	//private Logger log = LoggerFactory.getLogger(RedisCacheImpl.class);
	
	@Autowired
	private RedisTemplate<K,V> redisTemplate;

	
	public boolean hasKey(K key) {
		return redisTemplate.hasKey(key);
	}

	
	public void put(K key, V obj) {
		redisTemplate.opsForValue().set(key, obj);
	}

	
	public V get(K key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	
	

}
