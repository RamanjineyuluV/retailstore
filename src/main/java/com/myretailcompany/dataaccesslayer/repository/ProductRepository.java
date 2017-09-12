package com.myretailcompany.dataaccesslayer.repository;

import org.springframework.data.repository.CrudRepository;

import com.myretailcompany.dataaccesslayer.entity.Product;


public interface ProductRepository  extends CrudRepository<Product,Long> {
	
	public long count();
	public Product findByBarCodeId(String barCodeId);
	

}
