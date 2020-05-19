package com.ibm.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.ms.model.OrderHistory;

@Component
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
	

}
