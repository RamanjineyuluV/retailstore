package com.myretailcompany.rest.controller.bill.beans;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProductInfoForBill {

	public ProductInfoForBill() {
		super();
	}
	public ProductInfoForBill(String barCodeId, int quantity) {
		super();
		this.barCodeId = barCodeId;
		this.quantity = quantity;
	}
	@NotNull
	private String barCodeId;
	private int quantity;
	
	public String getBarCodeId() {
		return barCodeId;
	}
	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
	
}
