package com.myretailcompany.service.product;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretailcompany.dataaccesslayer.entity.Product;
import com.myretailcompany.dataaccesslayer.repository.ProductRepository;
import com.myretailcompany.rest.controller.ResourceNotFoundException;

@Service
public class ProductService {

	final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private ProductRepository productRepo;
	
	private void verifyProductExists(Long id) {
		logger.info("Verifying if the product exists with an id = " + id);
		Product product = productRepo.findOne(id);
		if (product == null) {
			throw new ResourceNotFoundException("Product with id " + id + " not found");
		}
	}

	// Read

	public Iterable<Product> getAllProducts() {
		Iterable<Product> products = productRepo.findAll();
		logger.info("returning all products");
		return products;
	}

	public Product getProductById(Long id) {
		verifyProductExists(id);
		Product product = productRepo.findOne(id);
		return product;
	}

	public Product createProduct(Product product) {
		logger.info("Input recieved to create product = " + product);
		product = productRepo.save(product);
		logger.info("Created product with id = " + product.getId());
		return product;

	}

	public Product updateProduct(Product product, Long id) {
		verifyProductExists(id);
		Product p = productRepo.save(product);
		logger.info("updated product id = " + product.getId());
		return p;
	}

	public void deleteProduct(Long id) {
		verifyProductExists(id);
		productRepo.delete(id);
	}

}
