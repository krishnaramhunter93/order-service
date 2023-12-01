package com.microservice.orderservice.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.request.OrderRequest;
import com.microservice.orderservice.response.OrderResponse;
import com.microservice.orderservice.service.OrderService;


@RestController // requests come from front end to controller
@RequestMapping(value="/microservice/v1/order")// it maps web requests url/endpoints
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	/*
	 * create - post 
	 * update - patch/put
	 * retrieve/read - get
	 * delete - delete
	 */
	
//	API - application programming interface - communication between request 
//	and response(REST API - Represntation state transfer API)
	
	// from controller requests go to service class
	
	@Autowired
	private OrderService orderService;
	
	//@RequestBody- it takes the input from the front end in json format and passes it to API
	@PostMapping(value="/save") //other way @RequestMapping(value="/save",method=RequestMethod.POST)
	public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {
		// call service method createOrder and return order body in the responseentity
		logger.info("saveOrder API has started");
		Order order = orderService.createOrder(orderRequest);
		return ResponseEntity.ok().body(order);
		
	}
	
	//@PathVariable - it will specified in the url
	@GetMapping(value="/find/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable("orderId") Long orderId) {
		// take orderId from the url and call findOrderById which is in orderService
		// and return the response binding to responseentity
		logger.info("getOrderById API has started");
		Optional<Order> cust = orderService.findOrderById(orderId);
		return ResponseEntity.ok().body(cust);
	}
	
	@GetMapping(value="/findall")
	public ResponseEntity<?> getAllOrders(){
		// call findAllOrders() present in orderService and get the list of orders present in database
		// and return the response binding to responseentity
		logger.info("getAllOrders API has started");
		List<OrderResponse> orderResponselist = orderService.findAllOrders();
		return ResponseEntity.ok().body(orderResponselist);
	}
	
	@DeleteMapping(value="/delete/{orderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") Long orderId){
		//call deleteOrderById method in service class and it will not return anything
		// so we are passing an explicit deleted message in responseentity
		logger.info("deleteOrderById API has started");
		orderService.deleteOrderById(orderId);
		return ResponseEntity.ok().body("The order id "+orderId+" got deleted!");
	}
	
	@PutMapping(value="/update/{orderId}")
	public ResponseEntity<?> updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable("orderId") Long orderId){
		// take orderrequest which contains new data to update and orderId which is used to fetch old data from datbase
		// call updateOrder of orderService class
		logger.info("updateOrder API has started");
		Order order = orderService.updateOrder(orderRequest, orderId);
		return ResponseEntity.ok().body(order);
	}
}
