package com.ibm.ms.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.ms.model.LoginCredential;
import com.ibm.ms.model.AccessToken;
import com.ibm.ms.repo.AccessTokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private AuthenticationManager authManager;
	
	private final AccessTokenConfig accessToken;
	
	private UserDetailsServiceImpl service;
	
	private String token;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
			AccessTokenConfig accessToken, UserDetailsServiceImpl service) {
		this.authManager = authenticationManager;
		this.accessToken = accessToken;
		this.service = service;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(accessToken.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException{
			logger.info(request.toString());	
		
try {
			
			// 1. Get credentials from request
			LoginCredential creds = new ObjectMapper().readValue(request.getInputStream(), LoginCredential.class);
			
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain  chain,Authentication auth)throws IOException,ServletException{
		
		logger.info(request.toString());
		Long now = System.currentTimeMillis();
		 token = Jwts.builder().setSubject(auth.getName())
				.claim("authorities", auth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + accessToken.getExpiration() * 1000))
				.signWith(SignatureAlgorithm.HS512, accessToken.getSecret().getBytes())
				.compact();
		logger.info("User:"+auth.getName()+"Token:"+token);
		service.saveToken(token,auth.getName());
		
		response.addHeader(accessToken.getHeader(),accessToken.getPrefix() + token);		
	}
	
	
	

}
