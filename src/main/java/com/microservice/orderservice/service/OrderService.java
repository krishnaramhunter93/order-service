package com.microservice.orderservice.service;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.orderservice.dao.OrderDao;
import com.microservice.orderservice.enums.OrderStatus;
import com.microservice.orderservice.model.Customer;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.Product;
import com.microservice.orderservice.request.OrderRequest;
import com.microservice.orderservice.response.OrderResponse;



@Service
public class OrderService {

	// from service the flow goes to OrderDao

	@Autowired
	private OrderDao orderDao;
	
	RestTemplate restTemplate = new RestTemplate();

//	@Autowired
//	private ProductDao productDao;
//
//	@Autowired
//	private CustomerDao customerDao;

	/*
	 * create - createOrder read / retrieve - findOrderById, findAllOrder update -
	 * updateOrder delete - deleteOrderById
	 */

	public Order createOrder(OrderRequest orderRequest) {
		// take all the request fields from orderRequest using getters
		// and set it in order model/entity class using setter
		// save that order model object into database
		// return the save order

		Order order = new Order();
		order.setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());
		// order.setProductTotalPrice(orderRequest.getProductTotalPrice());
		order.setDeliveryAddress(orderRequest.getDeliveryAddress());
		// order.setOrderStatus(orderRequest.getOrderStatus());
		// whenever an order is created by default it's status will be in progress
		order.setOrderStatus(OrderStatus.IN_PROGRESS.toString());

		// take customerid and productid from orderrequest and find corresponding
		// customer and product record from database using findbyid
//		Optional<Product> product = productDao.findById(orderRequest.getProductId());
//		Optional<Customer> customer = customerDao.findById(orderRequest.getCustomerId());

		order.setCustomerId(orderRequest.getCustomerId());
		order.setProductId(orderRequest.getProductId());
//		order.setCustomer(customer.get());
//		order.setProduct(product.get());
//
		
		Product productResponse = restTemplate.getForObject("http://localhost:7777/om/v1/product/find/"+orderRequest.getProductId(), Product.class);

		// get the product price from product table and store it in one variable
		String productPrice = productResponse.getPrice();
		// take productPurchaseQuantity from orderRequest and store it in one variable
		String productPurchaseQuantity = orderRequest.getProductPurchaseQuantity();
		// converting both above values into integer and multiplying and storing them in
		// one variable productTotalPrice
		int productTotalPrice = Integer.parseInt(productPrice) * Integer.parseInt(productPurchaseQuantity);
		// set the productTotalPrice into order object
		order.setProductTotalPrice(Integer.toString(productTotalPrice));
		
		
		order = orderDao.save(order);

		return order;

	}

	public Optional<Order> findOrderById(Long orderId) {
		// take orderId as input and find that order in the table if present else
		// not found
		Optional<Order> order = orderDao.findById(orderId);
		return order;

	}

	public List<OrderResponse> findAllOrders() {

		List<OrderResponse> orderResponseList = new ArrayList<>();
		// find all the orders in the database with findAll() method and that returns
		// list of orders
		List<Order> orderList = orderDao.findAll();
		
		for(Order order : orderList) {
			OrderResponse orderResponse = new OrderResponse();
			Product productResponse = restTemplate.getForObject("http://localhost:7777/om/v1/product/find/"+order.getProductId(), Product.class);
			Customer customerResponse = restTemplate.getForObject("http://localhost:7776/om/v1/customer/find/"+order.getCustomerId(), Customer.class);
			orderResponse.setOrderId(order.getOrderId());
			orderResponse.setOrderStatus(order.getOrderStatus());
			orderResponse.setProductPurchaseQuantity(order.getProductPurchaseQuantity());
			orderResponse.setProductTotalPrice(order.getProductTotalPrice());
			orderResponse.setDeliveryAddress(order.getDeliveryAddress());
			orderResponse.setCustomer(customerResponse);
			orderResponse.setProduct(productResponse);
			
			orderResponseList.add(orderResponse);
			
		}
		
		return orderResponseList;
		
		
	}

	public void deleteOrderById(Long orderId) {
		// takes orderId as input and deleted it in the database table order
		orderDao.deleteById(orderId);
	}

	public Order updateOrder(OrderRequest orderRequest, Long orderId) {
		// 1. get old/saved order values from database table using findByID and store
		// it in order object
		// 2. set order object old data to orderrequest object new data
		// 3. save order object to database

		// orderRequest contains new data that needs to be updated in database, coming
		// from the front end
		// order object contains old data from database
		// remove old data from order object and update it with the new data of
		// orderRequest
		Order updatedOrder = null;
		// step1
		Optional<Order> order = orderDao.findById(orderId);
		// step2
		order.get().setProductPurchaseQuantity(orderRequest.getProductPurchaseQuantity());
		// order.get().setProductTotalPrice(orderRequest.getProductTotalPrice());
		order.get().setDeliveryAddress(orderRequest.getDeliveryAddress());
		// order.get().setOrderStatus(orderRequest.getOrderStatus());

		// get orderStatus from orderRequest and check whether that orderStatus is
		// present in enum or not
		if (orderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.SHIPPED.toString())) {
			order.get().setOrderStatus(OrderStatus.SHIPPED.toString());
		} else if (orderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.DELIVERED.toString())) {
			order.get().setOrderStatus(OrderStatus.DELIVERED.toString());
		} else if (orderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.CANCELLED.toString())) {
			order.get().setOrderStatus(OrderStatus.CANCELLED.toString());
		} else if (orderRequest.getOrderStatus().equalsIgnoreCase(OrderStatus.RETURNED.toString())) {
			order.get().setOrderStatus(OrderStatus.RETURNED.toString());
		} else {
			order.get().setOrderStatus(OrderStatus.IN_PROGRESS.toString());
		}

//		Optional<Product> product = productDao.findById(orderRequest.getProductId());
//		Optional<Customer> customer = customerDao.findById(orderRequest.getCustomerId());
//
//		order.get().setCustomer(customer.get());
//		order.get().setProduct(product.get());
//
//		// get the product price from product table and store it in one variable
//		String productPrice = product.get().getPrice();
//		// take productPurchaseQuantity from orderRequest and store it in one variable
//		String productPurchaseQuantity = orderRequest.getProductPurchaseQuantity();
//		// converting both above values into integer and multiplying and storing them in
//		// one variable productTotalPrice
//		int productTotalPrice = Integer.parseInt(productPrice) * Integer.parseInt(productPurchaseQuantity);
//		// set the productTotalPrice into order object
//		order.get().setProductTotalPrice(Integer.toString(productTotalPrice));

		// step3
		updatedOrder = orderDao.save(order.get());

		return updatedOrder;

	}
}
