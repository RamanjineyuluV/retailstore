package com.myretailcompany.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretailcompany.dataaccesslayer.entity.Product;
import com.myretailcompany.dataaccesslayer.repository.ProductRepository;
import com.myretailcompany.rest.controller.CustomException;
import com.myretailcompany.rest.controller.product.beans.ProductInfo;

@Service
public class ProductService {

	final Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private ProductRepository productRepo;
	
	private void verifyProductExists(Long id) {
		logger.info("Verifying if the product exists with an id = " + id);
		Product product = productRepo.findOne(id);
		if (product == null) {
			throw new CustomException("Product with id " + id + " not found");
		}
	}

	private void verifyIfProductExists(String barCodeId) {
		List<Product> productsByBarCodeID = productRepo.findByBarCodeId(barCodeId);
		if (null!= productsByBarCodeID  && productsByBarCodeID.size()!=0) {
			logger.info("Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
			throw new CustomException("Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
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

	public Product createProduct(ProductInfo productInfo) {
		logger.info("Input recieved to create product = " + productInfo);
		verifyIfProductExists(productInfo.getBarCodeId());
		Product product = new Product();
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());
		
		product = productRepo.save(product);
		logger.info("Created product with id = " + product.getId());
		return product;

	}

	public Product updateProduct(ProductInfo productInfo, Long id) {
		verifyProductExists(id);
		Product product = productRepo.findOne(id);
		product.setBarCodeId(productInfo.getBarCodeId());
		product.setName(productInfo.getName());
		product.setProductCategory(productInfo.getProductCategory());
		product.setRate(productInfo.getRate());
		Product p = productRepo.save(product);
		logger.info("updated product id = " + product.getId());
		return p;
	}

	public void deleteProduct(Long id) {
		verifyProductExists(id);
		productRepo.delete(id);
	}

}