package com.johnfnash.learn.spring.cache.ehcache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EhCacheTestServiceTest {

	private ClassPathXmlApplicationContext context;
	private EhCacheTestService service;
	
	@Before
	public void setup() {
		context = new ClassPathXmlApplicationContext("spring-cache-ehcache.xml");
		service = (EhCacheTestService) context.getBean("ehcacheServiceBean");
	}
	
	@After
	public void close() {
		if(null != context) {
			context.close();
		}
	}
	
	@Test
	public void test() throws InterruptedException {
		System.out.println("第一次调用：" + service.getTimestamp("param"));
		Thread.sleep(2000);
		System.out.println("2秒之后调用：" + service.getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + service.getTimestamp("param"));
	}
	
}
