package com.ibm.ms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.ms.service.ProductOrderService;

@RestController
@RequestMapping(path = "/placeOrder")
public class ProductOrderController {
	Logger logger = LoggerFactory.getLogger(ProductOrderController.class);
	
	@Autowired
	ProductOrderService service;
	
	
	@RequestMapping(path="/",method = RequestMethod.POST )
	public String receiveOrder(@RequestParam List<String> productName){
		logger.info(productName.toString());
		return service.placeOrder(productName);
	}

}
