package com.ibm.ms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderHistory {
	
	@Id
	@GeneratedValue
	private int orderId;
	
	private int productId;
	

	public OrderHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderHistory(int orderId, int productId
			) {
		super();
		this.orderId = orderId;		
		this.productId = productId;
	}

	public double getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
}
