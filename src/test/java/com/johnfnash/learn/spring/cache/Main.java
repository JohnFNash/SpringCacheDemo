package com.johnfnash.learn.spring.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.johnfnash.learn.entity.Account;

public class Main {
	private ClassPathXmlApplicationContext context;
	private AccountService service;
	
	@Before
	public void setup() {
		context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");
		service = (AccountService) context.getBean("accountServiceBean");
	}
	
	@After
	public void close() {
		if(null != context) {
			context.close();
		}
	}
	
	@Test
	public void test1() {
		// 第一次查询，应该走数据库
		System.out.println("first query..."); 
		final String USER_NAME = "somebody";
		service.getAccountByName(USER_NAME);
		
		// 第二次查询，应该不查数据库，直接返回缓存的值
		System.out.println("second query...");
		service.getAccountByName(USER_NAME); 
		System.out.println();
		
		System.out.println("start testing clear cache..."); // 更新某个记录的缓存，首先构造两个账号记录，然后记录到缓存中
		Account account1 = service.getAccountByName("somebody1");
		@SuppressWarnings("unused")
		Account account2 = service.getAccountByName("somebody2");
		// 开始更新其中一个 account1.setId(1212);
		service.updateAccount(account1);
		service.getAccountByName("somebody1");// 因为被更新了，所以会查询数据库
		service.getAccountByName("somebody2");// 没有更新过，应该走缓存
		service.getAccountByName("somebody1");// 再次查询，应该走缓存
		System.out.println("------------------------------");
		// 清除所有缓存
		service.reload();
		service.getAccountByName("somebody1");// 应该会查询数据库
		service.getAccountByName("somebody2");// 应该会查询数据库
		service.getAccountByName("somebody1");// 应该走缓存
		service.getAccountByName("somebody2");// 应该走缓存
		
		context.close();
	}
	
	@Test
	public void test2() {
		service.getAccount("somebody", "123456", true);// 应该查询数据库
		service.getAccount("somebody", "123456", true);// 应该走缓存
		service.getAccount("somebody", "123456", false);// 应该走缓存
		service.getAccount("somebody", "654321", true);// 应该查询数据库
		service.getAccount("somebody", "654321", true);// 应该走缓存
	}
	
	@Test
	public void test3() {
		Account account = service.getAccountByName("aaa");
		account.setPassword("123");
		service.updateAccountAndCache(account);
		account.setPassword("321");
		service.updateAccountAndCache(account);
	}
	
	@Test
	public void test4() {
		// 如果对象的方法是内部调用（即 this 引用）而不是外部引用，
		// 则会导致 proxy 失效，那么我们的切面就失效
		service.getAccountByName2("aaa");
		service.getAccountByName2("aaa");
		service.getAccountByName2("aaa");
	}
	
	@Test
	public void test5() {
		// 如果对象的方法是内部调用（即 this 引用）而不是外部引用，
		// 则会导致 proxy 失效，那么我们的切面就失效
		service.getAccountByName2("aaa");
		service.getAccountByName2("aaa");
		service.getAccountByName2("aaa");
	}
	
	@Test
	public void test6() {
		// 如果对象的方法是内部调用（即 this 引用）而不是外部引用，
		// 则会导致 proxy 失效，那么我们的切面就失效
		service.getAccountByName("aaa");
		service.getAccountByName("aaa");
		try {
			service.reloadWithEx();
		} catch (Exception e) {
		}
		// 第三次也走缓存因为 reload 失败了
		service.getAccountByName("aaa");
	}
	
	
}
