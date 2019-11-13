package com.bah.msd.mcc.security;



public interface JWTUtil {
	public boolean verifyToken(String jwtToken);
	public String getScopes(String jwtToken) ;
	public Token createToken(String scope) ;
}