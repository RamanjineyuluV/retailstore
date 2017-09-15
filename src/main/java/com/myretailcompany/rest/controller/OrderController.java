package com.myretailcompany.rest.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.myretailcompany.rest.controller.order.beans.OrderUpdateInfo;
import com.myretailcompany.service.OrderService;
import com.myretailcompany.util.OrderStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
public class OrderController {
	final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	
	
	@ApiOperation(produces = "application/json", value = "fetches all orders from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    }
    )
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Order>> getAllorders() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}

	
			
	
	
	@ApiOperation(produces = "application/json", value = "fetches a particular order details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Order details"),
            @ApiResponse(code = 404, message = "Order Not Found")
    }
    )
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
	}


	
	@ApiOperation(produces = "application/json", value = "Creates an Order and returns orderId.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Id of the order")
     }
    )
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> createOrder() {
		logger.info("Request recieved to create Order = " );
		Order order =  orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		logger.info("Created Order with id = " + order.getId());
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId())
				.toUri();
		logger.info("Setting header url with newly created Order= " + order.getId());
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>("{\"id\":"+order.getId()+"}", responseHeaders, HttpStatus.CREATED);
	}

	@ApiOperation(produces = "application/json", value = "Add or Remove products from the Order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Id of the order"),
            @ApiResponse(code = 404, message = "Data validation error")
     }
    )
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateInfo orderupdateInfo, @PathVariable Long id) throws IOException {
		Order updated = orderService.updateOrder(orderupdateInfo, id);
		logger.info("Request recieved =  " + orderupdateInfo);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	}

	
	@ApiOperation(produces = "application/json", value = "Deletes Order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status of request"),
            @ApiResponse(code = 404, message = "Order does not exist")
     }
    )
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return new ResponseEntity<>("{\"status\": \"success\"}",HttpStatus.OK);
	}
	

}
