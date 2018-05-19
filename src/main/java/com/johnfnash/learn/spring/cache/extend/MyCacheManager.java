package com.johnfnash.learn.spring.cache.extend;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

public class MyCacheManager extends AbstractCacheManager {
	private Collection<? extends MyCache> caches;
	
	public void setCaches(Collection<? extends MyCache> caches) {
		this.caches = caches;
	}
	
	@Override
	protected Collection<? extends Cache> loadCaches() {
		return this.caches;
	}
	
}
