package com.ibm.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.ms.model.Product;

@Component
public interface ProductRepository extends JpaRepository<Product, Integer> {
		Product findByName(String name);

}
