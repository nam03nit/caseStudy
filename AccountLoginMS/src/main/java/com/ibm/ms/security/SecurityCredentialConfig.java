package com.ibm.ms.security;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityCredentialConfig  extends WebSecurityConfigurerAdapter{

	Logger logger = LoggerFactory.getLogger(SecurityCredentialConfig.class);

	@Autowired	
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	AccessTokenConfig jwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info(http.toString());
		http.headers().frameOptions().disable();
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint((req,resp,e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		.addFilter(new JwtAuthenticationFilter(authenticationManager(),jwtConfig,userDetailsService))
		.authorizeRequests()		
		.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		.and()
        .authorizeRequests().antMatchers("/h2/**").permitAll()

		.anyRequest().authenticated();
		System.out.println("configure end................................");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public AccessTokenConfig jwtConfig() {
        	return new AccessTokenConfig();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
