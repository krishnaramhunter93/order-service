package com.microservice.orderservice.response;

import javax.persistence.Column;

import com.microservice.orderservice.model.Customer;
import com.microservice.orderservice.model.Product;

import lombok.Data;

@Data
public class OrderResponse {

	private Long orderId;
	private String productPurchaseQuantity;
	private String orderStatus;
	private String productTotalPrice;
	private String deliveryAddress;
	private Customer customer;
	private Product product;

}
