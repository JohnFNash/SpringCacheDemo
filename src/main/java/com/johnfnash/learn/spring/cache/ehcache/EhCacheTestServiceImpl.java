package com.johnfnash.learn.spring.cache.ehcache;

import org.springframework.cache.annotation.Cacheable;

public class EhCacheTestServiceImpl implements EhCacheTestService {

	@Cacheable(value="cacheTest", key="#param")
	@Override
	public String getTimestamp(String param) {
		Long timestamp = System.currentTimeMillis();
		return timestamp.toString();
	}

}
