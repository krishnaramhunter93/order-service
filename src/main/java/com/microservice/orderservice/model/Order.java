package com.microservice.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // generates getter and setters
@Entity//refers that this is an entity class
@Table(name="order")// creates table product in database oms
@AllArgsConstructor // all fields constructor
@NoArgsConstructor // no field constructor
public class Order {
	
	@Id // refers to primary key of the table
	@Column(name="order_id", nullable=false)// column corresponds to table column and it cannot be null
	@GeneratedValue(strategy = GenerationType.AUTO)// automatically generates id value
	private Long orderId;
	
	@Column(name="product_purchase_quantity", nullable=false)
	private String productPurchaseQuantity;
	
	@Column(name="order_status", nullable=false)
	private String orderStatus;
	
	@Column(name="product_total_price", nullable=false)
	private String productTotalPrice;
	
	@Column(name="delivery_address", nullable=false)
	private String deliveryAddress;
	
	@Column(name="product_id", nullable=false)
	private Long productId;
	
	@Column(name="customer_id", nullable=false)
	private Long customerId;


}
