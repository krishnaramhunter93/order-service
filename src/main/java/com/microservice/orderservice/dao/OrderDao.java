package com.microservice.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.orderservice.model.Order;


@Repository
public interface OrderDao extends JpaRepository<Order, Long>{

	//select * from oms.order where order_status='IN_PROGRESS' or order_status='SHIPPED';
	
	// use above query as value and write our own nativequery and call it from service and controller class
}
