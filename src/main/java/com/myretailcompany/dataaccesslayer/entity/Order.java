package com.myretailcompany.dataaccesslayer.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.myretailcompany.util.OrderStatus;

@Entity
@Table (name="ORDERS")

public class Order {



	public Order(double totalValue, int noOfItems,OrderStatus orderStatus) {
		super();
		this.totalValue = totalValue;
		this.noOfItems = noOfItems;
		this.orderStatus = orderStatus;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double totalValue;
	private int noOfItems;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@OneToMany
	private List<LineItem> lineItems;
	
	public Order(List<LineItem> lineItems) {
		super();
		this.lineItems = lineItems;
	}
	

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	

	
	
	
	
}
