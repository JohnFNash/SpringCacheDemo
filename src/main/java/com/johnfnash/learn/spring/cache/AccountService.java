package com.johnfnash.learn.spring.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.johnfnash.learn.entity.Account;

@CacheConfig(cacheNames = "accountCache") // 配置全局性缓存名称
public class AccountService {

	// 使用了一个缓存名叫 accountCache
	// 这里的缓存中的 key 就是参数 userName，value 就是 Account 对象
	// 只有账号名称的长度小于等于 4 的情况下，才做缓存
	//@Cacheable(value = "accountCache", condition = "#userName.length() <= 4")
	@Cacheable(value = "accountCache")
	public Account getAccountByName(String userName) {
		// 使用了一个缓存名叫 accountCache
		System.out.println("real query account." + userName);
		return getFromDB(userName);
	}
	
	@Cacheable(value = "accountCache", key = "#userName.concat(#password)")
	public Account getAccount(String userName, String password, boolean sendLog) {
		return getFromDB(userName, password);
	}

	// 清空 accountCache 缓存 中指定键, cache 名称: accountCache, cache key:
	// #account.getName()
	@CacheEvict(value = "accountCache", key = "#account.getName()", beforeInvocation = true)
	public void updateAccount(Account account) {
		updateDB(account);
	}
	
	// 更新 accountCache 缓存
	@CachePut(value="accountCache", key="#account.getName()")
	public void updateAccountAndCache(Account account) {
		updateDB(account);
	}

	// 清空 accountCache 缓存中所有键
	@CacheEvict(value = "accountCache", allEntries = true)
	public void reload() {
		
	}
	
	//@CacheEvict 注释有一个属性 beforeInvocation，缺省为 false，
	//即缺省情况下，都是在实际的方法执行完成后，才对缓存进行清空操作。
	//期间如果执行方法出现异常，则会导致缓存清空不被执行
	@CacheEvict(value = "accountCache", allEntries = true, beforeInvocation = true)
	public void reloadWithEx() {
		throw new RuntimeException();
	}

	public Account getAccountByName2(String userName) {
		return this.getAccountByName(userName);
	}
	
	private Account getFromDB(String acctName) {
		System.out.println("real querying db..." + acctName);
		return new Account(acctName);
	}
	
	private Account getFromDB(String acctName, String password) {
		System.out.println("real querying db... userName=" + acctName + " password=" + password);
		return new Account(acctName, password);
	}

	private void updateDB(Account account) {
		System.out.println("real update db..." + account.getName());
	}

}
