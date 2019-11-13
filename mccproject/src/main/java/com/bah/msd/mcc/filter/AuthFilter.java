package com.bah.msd.mcc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.bah.msd.mcc.security.JWTHelper;
import com.bah.msd.mcc.security.JWTUtil;



@Component
public class AuthFilter implements Filter {

	JWTUtil jwtUtil = new JWTHelper();
	
	private static final String API_SCOPE = "com.api.customer.r";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

			// check JWT token for all url requests to data api
			String authheader = req.getHeader("authorization");
			if (authheader != null && authheader.length() > 7 && authheader.startsWith("Bearer")) {
				String jwt_token = authheader.substring(7, authheader.length());
				if (jwtUtil.verifyToken(jwt_token)) {
					String request_scopes = jwtUtil.getScopes(jwt_token);
					if (request_scopes.contains(API_SCOPE)) {
						// continue on to api
						chain.doFilter(request, response);
						return;
					}
				}
			}
		//}

		// reject request and return error instead of data
		res.sendError(HttpServletResponse.SC_FORBIDDEN, "failed authentication");
	}
}