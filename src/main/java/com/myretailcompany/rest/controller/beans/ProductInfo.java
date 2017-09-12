package com.myretailcompany.rest.controller.beans;

public class ProductInfo {

	public ProductInfo(long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	private long productId;
	private int quantity;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("productId: ").append(productId).append("  ,  ").append(" quantity = ").append(quantity);
		return sb.toString();
	}
	
	
	
}
