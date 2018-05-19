package com.johnfnash.learn.spring.cache.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.johnfnash.learn.entity.Account;
import com.johnfnash.learn.spring.cache.AccountService;

public class RedisTestServiceTest {

	private ClassPathXmlApplicationContext context;
	private AccountService service;
	
	@Before
	public void setup() {
		context = new ClassPathXmlApplicationContext("spring-cache-redis.xml");
		service = (AccountService) context.getBean("accountServiceBean");
	}
	
	@After
	public void close() {
		if(null != context) {
			context.close();
		}
	}
	
	@Test
	public void test() throws InterruptedException {
		service.updateAccount(new Account("aaaa")); //先清除之前的数据
		
		service.getAccountByName("aaaa"); // 第一次 gey key, put key
		service.getAccountByName("aaaa"); // 已经存在，get key，不再执行getAccountByName内部代码
		service.getAccountByName("aaaa"); // 已经存在，get key，不再执行getAccountByName内部代码
	}
	
}
