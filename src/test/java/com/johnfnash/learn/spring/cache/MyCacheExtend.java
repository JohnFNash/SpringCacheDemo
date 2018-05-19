package com.johnfnash.learn.spring.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.johnfnash.learn.entity.Account;

public class MyCacheExtend {

	private ClassPathXmlApplicationContext context;
	private AccountService service;
	
	@Before
	public void setup() {
		context = new ClassPathXmlApplicationContext("spring-cache-self-cache.xml");
		service = (AccountService) context.getBean("accountServiceBean");
	}
	
	@After
	public void close() {
		if(null != context) {
			context.close();
		}
	}
	
	@Test
	public void test() {
		Account account = service.getAccountByName("aaa"); 
		System.out.println("passwd="+account.getPassword());
		account = service.getAccountByName("aaa");
		// 密码发生了改变
		System.out.println("passwd="+account.getPassword());
	}
	
}
