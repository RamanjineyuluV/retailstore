package com.myretailcompany;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.rest.controller.order.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.order.beans.ProductInfoForOrder;
import com.myretailcompany.rest.controller.product.beans.ProductInfo;
import com.myretailcompany.service.OrderService;
import com.myretailcompany.service.ProductService;
import com.myretailcompany.util.OrderStatus;
import com.myretailcompany.util.ProductCategory;

@Component
public class SampleDataSetupRunner implements CommandLineRunner {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Inside Runner..");
		setUpProductData();
		setupOrderData();
		logger.info("Exiting Runner.. ");
	}

	private void setUpProductData() {
		productService.createProduct(new ProductInfo("ABC-abc-0001", 20.0, "Tomato", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0002", 30.0, "Onion", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0003", 40.0, "Potato", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0004", 50.0, "Bread", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0005", 60.0, "Apples", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0006", 70.0, "Banana", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0007", 80.0, "Strawberry", ProductCategory.A));
		productService.createProduct(new ProductInfo("ABC-abc-0008", 90.0, "Apricot", ProductCategory.B));
		productService.createProduct(new ProductInfo("ABC-abc-0009", 100.0, "Raisins", ProductCategory.C));
		productService.createProduct(new ProductInfo("ABC-abc-0010", 110.0, "CashewNut", ProductCategory.A));
	}

	public void setupOrderData() {

		// create a new order to update information.
		Order o1 = orderService.createOrder(new Order(0.0, 0, OrderStatus.IN_PROGRESS));

		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();

		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0001", 2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0002", 2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0003", 2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0004", 2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0005", 2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);

		System.out.println("orderupdateInfo = " + orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = " + retrieveUpdatedOrder.getNoOfItems() + "  value ="
				+ retrieveUpdatedOrder.getTotalValue());

	}
}
