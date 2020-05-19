package com.ibm.ms.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.ms.model.LoginCredential;
import com.ibm.ms.model.AccessToken;
import com.ibm.ms.repo.LoginCredenatilsRepository;
import com.ibm.ms.repo.AccessTokenRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	LoginCredenatilsRepository credentailsRepo;
	
	@Autowired
	AccessTokenRepository tokenRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Optional<LoginCredential>  userCredenatils = credentailsRepo.findById(username);
		if(userCredenatils != null)	{	
				return new User(userCredenatils.get().getUsername(), encoder.encode(userCredenatils.get().getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
		}
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	public void saveToken(String token, String name) {
		tokenRepo.save(new AccessToken(name, token));
		
	}
	
	

}
