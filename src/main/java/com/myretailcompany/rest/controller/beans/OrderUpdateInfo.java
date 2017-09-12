package com.myretailcompany.rest.controller.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.myretailcompany.util.OrderStatus;

public class OrderUpdateInfo {
	private List<ProductInfo> productsToBeAdded;
	private List<ProductInfo> productsToBeRemoved;
	
	@NotNull
	private OrderStatus status;

	public List<ProductInfo> getProductsToBeAdded() {
		return productsToBeAdded;
	}
	public void setProductsToBeAdded(List<ProductInfo> productsToBeAdded) {
		this.productsToBeAdded = productsToBeAdded;
	}
	public List<ProductInfo> getProductsToBeRemoved() {
		return productsToBeRemoved;
	}
	public void setProductsToBeRemoved(List<ProductInfo> productsToBeRemoved) {
		this.productsToBeRemoved = productsToBeRemoved;
	}

	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("status: ").append(status).append("  ,  ").append(" productsToBeAdded = ").append(productsToBeAdded).append(" productsToBeRemoved = ").append(productsToBeRemoved);
		return sb.toString();
	}
	
}
