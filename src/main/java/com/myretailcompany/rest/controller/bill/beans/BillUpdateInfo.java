package com.myretailcompany.rest.controller.order.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.myretailcompany.util.OrderStatus;

public class OrderUpdateInfo {
	private List<ProductInfoForOrder> productsToBeAdded;
	private List<ProductInfoForOrder> productsToBeRemoved;
	
	public OrderUpdateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@NotNull
	private OrderStatus status;

	public List<ProductInfoForOrder> getProductsToBeAdded() {
		return productsToBeAdded;
	}
	public void setProductsToBeAdded(List<ProductInfoForOrder> productsToBeAdded) {
		this.productsToBeAdded = productsToBeAdded;
	}
	public List<ProductInfoForOrder> getProductsToBeRemoved() {
		return productsToBeRemoved;
	}
	public void setProductsToBeRemoved(List<ProductInfoForOrder> productsToBeRemoved) {
		this.productsToBeRemoved = productsToBeRemoved;
	}

	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
