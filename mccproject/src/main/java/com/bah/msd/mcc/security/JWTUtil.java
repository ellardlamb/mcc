package com.bah.msd.mcc.security;

public interface JWTUtil {
	//public boolean verifyToken(String jwtToken, String secret);
	//public String getScopes(String jwtToken, String secret);
	//public Token createToken(String secret);
	public boolean verifyToken(String jwtToken);
	public String getScopes(String jwtToken);
	public Token createToken(String jwtToken);
}