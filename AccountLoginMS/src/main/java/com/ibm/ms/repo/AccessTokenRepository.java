package com.ibm.ms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.ms.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

}
