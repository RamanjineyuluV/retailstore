package com.myretailcompany.rest.controller;

import java.net.URI;

import javax.validation.Valid;

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

import com.myretailcompany.dataaccesslayer.entity.Product;
import com.myretailcompany.service.product.ProductService;

// Entity Beans are used and returned by this call to web layer. Ideally they should be different.

@RestController
public class ProductController {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductService productService;

	// Read
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}

	// Create
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
		logger.info("Input recieved to create product = " + product);
		product = productService.createProduct(product);
		logger.info("Created product with id = " + product.getId());

		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
				.toUri();
		logger.info("Setting header url with newly created product= " + product.getId());
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>("{\"id\":"+product.getId()+"}", responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		Product p = productService.updateProduct(product, id);
		logger.info("updated product id = " + product.getId());
		return new ResponseEntity<>("{\"status\": \"success\"}",HttpStatus.OK);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("{\"status\": \"success\"}",HttpStatus.OK);
	}

}
