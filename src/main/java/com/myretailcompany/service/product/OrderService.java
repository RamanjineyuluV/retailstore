package com.myretailcompany.service.product;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.dataaccesslayer.repository.OrderRepository;
import com.myretailcompany.rest.controller.ResourceNotFoundException;
import com.myretailcompany.rest.controller.beans.OrderUpdateInfo;
import com.myretailcompany.util.OrderStatus;

@Service
public class OrderService {

	final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private OrderRepository orderRepo;
	
	private void verifyOrderExists(Long id) {
		logger.info("Verifying if the product exists with an id = " + id);
		Order order = orderRepo.findOne(id);
		if (order == null) {
			throw new ResourceNotFoundException("Order with id " + id + " not found");
		}
	}

	// Read

	public Iterable<Order> getAllOrders() {
		Iterable<Order> order = orderRepo.findAll();
		logger.info("returning all products");
		return order;
	}

	public Order getOrderById(Long id) {
		verifyOrderExists(id);
		Order order = orderRepo.findOne(id);
		return order;
	}

	public Order createOrder(Order order) {
		logger.info("Input recieved to create product = " + order);
		order = orderRepo.save(order);
		logger.info("Created product with id = " + order.getId());
		return order;

	}

	public Order updateOrder(OrderUpdateInfo orderupdateInfo, Long id) {
		verifyOrderExists(id);
		if (null==orderupdateInfo){
			throw new ResourceNotFoundException("There is no information to be updated for id " + id ); // TODO: Add a new type of exception.
		}
		if  (orderupdateInfo.getStatus().equals(OrderStatus.COMPLETED)){
			// Order has been completed.  
			
		} else if (orderupdateInfo.getStatus().equals(OrderStatus.RELEASED)){
			// Add the products if any, compute the total value.Return the current order data.
			
			
		}else if (orderupdateInfo.getStatus().equals(OrderStatus.CREATED)){
			// Add the products and compute the total value as per current list. Return the current order data.
		}
		
		//update the status of the order.
		// return current object status
		
		
		Order o1 = orderRepo.findOne(id)
		logger.info("updated product id = " + o1.getId());
		return o1;
	}

	public void deleteOrder(Long id) {
		verifyOrderExists(id);
		orderRepo.delete(id);
	}


}
