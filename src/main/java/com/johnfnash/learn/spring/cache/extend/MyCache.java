package com.johnfnash.learn.spring.cache.extend;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.johnfnash.learn.entity.Account;

public class MyCache implements Cache {
	private String name;
	private ConcurrentHashMap<String, Account> store = new ConcurrentHashMap<String, Account>();
	
	public MyCache() {
	}
	
	public MyCache(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getNativeCache() {
		return store;
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper result = null;
		Account theValue = store.get(key);
		if(null != theValue) {
			theValue.setPassword("from mycache: " + name);
			result = new SimpleValueWrapper(theValue);
		}
		return result;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return null;
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		Account theValue = (Account) value;
		store.put((String) key, theValue);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return new SimpleValueWrapper(store.putIfAbsent((String)key, (Account)value));
	}

	@Override
	public void evict(Object key) {
		store.remove(key);
	}

	@Override
	public void clear() {
		store.clear();
	}

}
