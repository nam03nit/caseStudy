package com.ibm.ms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.ms.model.LoginCredential;

@Component
public interface LoginCredenatilsRepository extends JpaRepository<LoginCredential, String>{

}
