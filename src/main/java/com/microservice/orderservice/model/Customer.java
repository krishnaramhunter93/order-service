package com.microservice.orderservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // generates getter and setters
public class Customer {
	
	private Long customerId;
	private String customerName;
	private String customerPassword;
	private String email;
	private String mobile;
	private String address;

}
