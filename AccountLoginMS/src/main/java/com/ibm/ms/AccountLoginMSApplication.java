package com.ibm.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ibm.ms.model.LoginCredential;
import com.ibm.ms.repo.LoginCredenatilsRepository;

@SpringBootApplication
@EnableEurekaClient
public class AccountLoginMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountLoginMSApplication.class, args);
	}
	
	@Autowired
	LoginCredenatilsRepository userRepo;
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			userRepo.save(new LoginCredential("user1","passw1rd"));
			userRepo.save(new LoginCredential("user2","password"));
			userRepo.save(new LoginCredential("admin","admin"));

		};
	}
	

}


