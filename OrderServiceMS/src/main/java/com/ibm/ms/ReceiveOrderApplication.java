package com.ibm.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ibm.ms.model.Product;
import com.ibm.ms.repository.ProductRepository;

@SpringBootApplication
@EnableEurekaClient
public class ReceiveOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiveOrderApplication.class, args);
	}
	
	@Autowired
	private ProductRepository repo;

	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {			
			repo.save(new Product(1, "One Plus Mobile", "64-bit", 30000.00));
			repo.save(new Product(2, "Lets Us C Book", "2019 Edition", 200.00));
			repo.save(new Product(3, "Cup set", "Fabric-Plastic 6 pieces",  70.00));						

		};
	}

	
	
}
