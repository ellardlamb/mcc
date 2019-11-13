package com.bah.msd.mcc.valueobject;

public class TokenRequestData {
	private String name;
	private String password;
	
	public TokenRequestData() {
		super();
	}

	public TokenRequestData(String name, String password) {
		super();
		this.name = name;
		this.password = password;
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
