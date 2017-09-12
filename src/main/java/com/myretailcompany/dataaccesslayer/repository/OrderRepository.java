package com.myretailcompany.dataaccesslayer.repository;

import org.springframework.data.repository.CrudRepository;
import com.myretailcompany.dataaccesslayer.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
