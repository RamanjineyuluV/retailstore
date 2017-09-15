package com.myretailcompany.dataaccesslayer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.myretailcompany.util.ProductCategory;

@Entity
@Table(name = "PRODUCT_MASTER")

public class Product {

	
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String barCodeId, double rate, String name, ProductCategory productCategory) {
		super();
		this.barCodeId = barCodeId;
		this.rate = rate;
		this.name = name;
		this.productCategory = productCategory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column(unique=true)
	private String barCodeId;
	
	@NotNull
	private double rate;
	
	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;

	public String getBarCodeId() {
		return barCodeId;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
