package com.johnfnash.learn.manual.cache;

import com.johnfnash.learn.entity.Account;

public class MyAccountService {
	
	private MyCacheManager<Account> cacheManager;
	
	public MyAccountService() {
		this.cacheManager = new MyCacheManager<Account>();
	}
	
	public Account getAccountByName(String acctName) {
		Account result = cacheManager.getValue(acctName); //首先查询缓存
		if(null != result) {
			System.out.println("get from cache..."+ acctName); 
		} else {
			result = getFromDB(acctName); // 否则到数据库中查询
			if(null != result) {
				// 将数据库查询的结果更新到缓存中
				cacheManager.addOrUpdate(acctName, result);
			}
		}
		return result;
	}
	
	public void reload() {
		cacheManager.evictCache();
	}
	
	private Account getFromDB(String acctName) {
		System.out.println("real querying db..." + acctName);
		return new Account(acctName);
	}
	
}
