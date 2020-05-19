package com.ibm.ms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.ms.model.OrderHistory;
import com.ibm.ms.model.Product;
import com.ibm.ms.repository.OrderHistoryRepository;
import com.ibm.ms.repository.ProductRepository;


@Service
public class ProductOrderService {
	
	Logger logger = LoggerFactory.getLogger(ProductOrderService.class);

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private OrderHistoryRepository orderHistoryRepo;


	public String placeOrder(List<String> products) {
		// TODO Auto-generated method stub
		boolean validList = false;
		
		for(String item :products) {
			
			Product prodItem = productRepo.findByName(item);
			if(prodItem!=null) {
				int orderId =(int) Math.random();
				orderHistoryRepo.save(new OrderHistory(orderId,prodItem.getId()));
				validList = true;
			}
		}
		
		if(validList == true) {
			return "Order is placed";
			
		}else {
			return "Requested product not found";
		}
		
		}
}
