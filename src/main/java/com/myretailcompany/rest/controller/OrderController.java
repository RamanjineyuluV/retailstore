package com.myretailcompany.rest.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.rest.controller.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.beans.ProductInfo;
import com.myretailcompany.service.product.OrderService;
import com.myretailcompany.util.OrderStatus;


@RestController
public class OrderController {
	final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	// Read
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Order>> getAllorders() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
	}

	// Create a new order and return the id along with reference URL. This method does not accept any input.
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> createOrder() {
		logger.info("Request recieved to create Order = " );
		Order order =  orderService.createOrder(new Order(0.0,0,OrderStatus.CREATED));
		logger.info("Created Order with id = " + order.getId());
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId())
				.toUri();
		logger.info("Setting header url with newly created Order= " + order.getId());
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>("{\"id\":"+order.getId()+"}", responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateInfo orderupdateInfo, @PathVariable Long id) throws IOException {
		orderService.updateOrder(orderupdateInfo, id);
		logger.info("Request recieved =  " + orderupdateInfo);
		return new ResponseEntity<>("{\"status\": \"success\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return new ResponseEntity<>("{\"status\": \"success\"}",HttpStatus.OK);
	}
	

}
