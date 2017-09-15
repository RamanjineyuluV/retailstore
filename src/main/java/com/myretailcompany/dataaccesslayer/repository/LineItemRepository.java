package com.myretailcompany.dataaccesslayer.repository;

import org.springframework.data.repository.CrudRepository;

import com.myretailcompany.dataaccesslayer.entity.LineItem;

public interface LineItemRepository extends CrudRepository<LineItem, Long>{
	
}
