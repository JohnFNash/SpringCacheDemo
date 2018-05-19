package com.johnfnash.learn.entity;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 9131597932330806613L;
	
	private int id;
	private String name;
	private String password; 
	
	public Account() {
		super();
	}
	
	public Account(String name) {
		super();
		this.name = name;
	}

	public Account(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
