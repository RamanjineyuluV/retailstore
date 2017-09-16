package com.myretailcompany.rest.controller.bill.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.myretailcompany.util.BillStatus;

public class BillUpdateInfo {
	private List<ProductInfoForBill> productsToBeAdded;
	private List<ProductInfoForBill> productsToBeRemoved;
	
	public BillUpdateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@NotNull
	private BillStatus status;

	public List<ProductInfoForBill> getProductsToBeAdded() {
		return productsToBeAdded;
	}
	public void setProductsToBeAdded(List<ProductInfoForBill> productsToBeAdded) {
		this.productsToBeAdded = productsToBeAdded;
	}
	public List<ProductInfoForBill> getProductsToBeRemoved() {
		return productsToBeRemoved;
	}
	public void setProductsToBeRemoved(List<ProductInfoForBill> productsToBeRemoved) {
		this.productsToBeRemoved = productsToBeRemoved;
	}

	
	public BillStatus getStatus() {
		return status;
	}
	public void setStatus(BillStatus status) {
		this.status = status;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
