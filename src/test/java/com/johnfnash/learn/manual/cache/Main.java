package com.johnfnash.learn.manual.cache;

public class Main {

	public static void main(String[] args) {
		MyAccountService service = new MyAccountService();
		// 开始查询账号
		final String ACCT_NAME = "somebody";
		
		service.getAccountByName(ACCT_NAME); // 第一次查询，应该是数据库查询
		service.getAccountByName(ACCT_NAME); // 第二次查询，应该是直接从缓存返回
		
		service.reload(); // 重置缓存
		System.out.println("after reload...");
		
		service.getAccountByName(ACCT_NAME); // 应该是数据库查询
		service.getAccountByName(ACCT_NAME);// 第二次查询，应该直接从缓存返回
		
	}
	
}
