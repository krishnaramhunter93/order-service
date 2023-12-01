package com.microservice.orderservice.request;

import lombok.Data;

@Data
public class OrderRequest {

	private String productPurchaseQuantity;
	private String orderStatus;
	private String productTotalPrice;
	private String deliveryAddress;
	private Long productId;
	private Long customerId;
}
