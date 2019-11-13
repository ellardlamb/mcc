package com.bah.msd.mcc.util;

import com.bah.msd.mcc.domain.Token;

public interface JWTUtil {
	public boolean verifyToken(String jwtToken);
	public String getScopes(String jwtToken) ;
	public Token createToken(String scope) ;
}