package com.myretailcompany.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.rest.controller.CustomException;
import com.myretailcompany.rest.controller.order.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.order.beans.ProductInfoForOrder;
import com.myretailcompany.util.OrderStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void testCreateOrder(){
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		Order o2 = orderService.getOrderById(o1.getId());
		assertThat(o1.getId()).isEqualTo(o2.getId());
	}
	
	@Test
	public void testOrderUpdateAddSingleProductCatA(){
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0001",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue());
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(20*2*1.1);
	}
	
	@Test
	public void testOrderUpdateAddSingleProductCatB(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0002",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue());
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(30*2*1.2);

		
	}
	
	
	@Test
	public void testOrderUpdateAddSingleProductCatC(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0003",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue());
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(40*2*1);
	}
	
	
	@Test
	public void testOrderUpdateAddMultipleProducts(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0001",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0002",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0003",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0004",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0005",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue());
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(5); //5 products add
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(20*2*1.1+30*2*1.2+40*2*1+50*2*1.1+60*2*1.2);
	}
	
	@Test
	public void testOrderUpdateAddAndRemoveProducts(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0001",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0002",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0003",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0004",2));
		productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-0005",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		
		productsToBeRemoved.add(new ProductInfoForOrder("ABC-abc-0005",2));
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue()+"   list of items = "+retrieveUpdatedOrder.getLineItems());
		
		
		
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(4); //5 products add
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(20*2*1.1+30*2*1.2+40*2*1+50*2*1.1);
	}
	
	
	
	
	@Test(expected=CustomException.class)
	public void testOrderUpdateAddNonExistingProduct(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeAdded.add(new ProductInfoForOrder("DDD-abc-0003",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue()+"   list of items = "+retrieveUpdatedOrder.getLineItems());
		assertThat(retrieveUpdatedOrder.getNoOfItems()).isEqualTo(1);
		assertThat(retrieveUpdatedOrder.getTotalValue()).isEqualTo(40*2*1);
	}
	

	@Test(expected=CustomException.class)
	public void testOrderUpdateRemoveNonExistingProduct(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeRemoved.add(new ProductInfoForOrder("DDD-abc-0003",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue()+"   list of items = "+retrieveUpdatedOrder.getLineItems());

	}
	
	@Test(expected=CustomException.class)
	public void testOrderNotExist(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeRemoved.add(new ProductInfoForOrder("DDD-abc-0003",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, (long)9999);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue()+"   list of items = "+retrieveUpdatedOrder.getLineItems());

	}
	
	@Test(expected=CustomException.class)
	public void testOrderUpdateRemoveExistingProductFromEmptyOrder(){
		
		//create a new order to update information.
		Order o1 =orderService.createOrder(new Order(0.0,0,OrderStatus.IN_PROGRESS));
		
		Long orderId = o1.getId();
		OrderUpdateInfo orderupdateInfo = new OrderUpdateInfo();
		List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
		List<ProductInfoForOrder> productsToBeRemoved = new ArrayList<ProductInfoForOrder>();
		
		productsToBeRemoved.add(new ProductInfoForOrder("ABC-abc-0001",2));
		orderupdateInfo.setProductsToBeAdded(productsToBeAdded);
		orderupdateInfo.setProductsToBeRemoved(productsToBeRemoved);
		orderupdateInfo.setStatus(OrderStatus.RELEASED);
		
		System.out.println("orderupdateInfo = "+orderupdateInfo);
		orderService.updateOrder(orderupdateInfo, orderId);
		Order retrieveUpdatedOrder = orderService.getOrderById(o1.getId());
		System.out.println("retrieveUpdatedOrder = "+retrieveUpdatedOrder.getNoOfItems()+"  value ="+retrieveUpdatedOrder.getTotalValue()+"   list of items = "+retrieveUpdatedOrder.getLineItems());

	}

	
	
	


}
