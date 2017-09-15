package com.myretailcompany.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretailcompany.dataaccesslayer.entity.LineItem;
import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.dataaccesslayer.entity.Product;
import com.myretailcompany.dataaccesslayer.repository.LineItemRepository;
import com.myretailcompany.dataaccesslayer.repository.OrderRepository;
import com.myretailcompany.dataaccesslayer.repository.ProductRepository;
import com.myretailcompany.rest.controller.CustomException;
import com.myretailcompany.rest.controller.order.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.order.beans.ProductInfoForOrder;
import com.myretailcompany.util.ProductCategory;

@Service
public class OrderService {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private LineItemRepository lineItemRepo;

	private void verifyOrderExists(Long id) {
		Order order = orderRepo.findOne(id);
		if (order == null) {
			throw new CustomException("Order with id " + id + " not found");
		}
		logger.info(" Order exists with an id = " + id);
	}

	// Read

	public Iterable<Order> getAllOrders() {
		Iterable<Order> order = orderRepo.findAll();
		logger.info("returning all products");
		return order;
	}

	public Order getOrderById(Long id) {
		verifyOrderExists(id);
		Order order = orderRepo.findOne(id);
		return order;
	}

	public Order createOrder(Order order) {
		logger.info("Input recieved to create Order = " + order);
		order = orderRepo.save(order);
		logger.info("Created product with id = " + order.getId());
		return order;

	}

	public Order updateOrder(OrderUpdateInfo orderupdateInfo, Long orderId) {

		logger.info("Request recieved for update of  : "+orderId);
		if (null == orderupdateInfo) {
			throw new CustomException("There is no information to be updated for id " + orderId); 
		}
		verifyOrderExists(orderId);

		logger.info("Processing products to be added");
		if (null!= orderupdateInfo.getProductsToBeAdded()){
			List<ProductInfoForOrder> prodToBeAdded = orderupdateInfo.getProductsToBeAdded();
			Iterator<ProductInfoForOrder> prodToBeAddedIter = prodToBeAdded.iterator();
			while (prodToBeAddedIter.hasNext()){
				ProductInfoForOrder pInfo = prodToBeAddedIter.next();
				logger.debug("Product to be added : "+pInfo);
				addProductToOrder(orderId, pInfo.getBarCodeId(),pInfo.getQuantity());
			}
		}
		
		logger.info("Processing products to be removed");
		if (null!= orderupdateInfo.getProductsToBeRemoved()){
			List<ProductInfoForOrder> prodToBeRemoved = orderupdateInfo.getProductsToBeRemoved();
			Iterator<ProductInfoForOrder> prodToBeRemovedIter = prodToBeRemoved.iterator();
			while (prodToBeRemovedIter.hasNext()){
				ProductInfoForOrder pInfo = prodToBeRemovedIter.next();
				logger.info("Product to be removed : "+pInfo);
				removeProductFromOrder(orderId, pInfo.getBarCodeId());
			}
		}
		
		Order order = orderRepo.findOne(orderId);
		order.setOrderStatus(orderupdateInfo.getStatus());
		logger.info("Computing total for the order");
		computeTotalValues(order);
		return order;
	}

	private void computeTotalValues(Order order) {

		int noOfItems=0;
		double totalValue=0;
		double totalCost=0;

		if (null!= order.getLineItems()){
			List<LineItem> lineItems = order.getLineItems();
			Iterator<LineItem> lineItemsIter = lineItems.iterator();
			while (lineItemsIter.hasNext()){
				LineItem li = lineItemsIter.next();
				double saleValue = computeValueForItem(li.getQuantity(),li.getProduct().getProductCategory(),li.getProduct().getRate());
				logger.debug("SaleValue &  Line Item  : "+saleValue+"   "+li);
				totalValue += saleValue;
				totalCost+= li.getQuantity() * li.getProduct().getRate();
				noOfItems++;
			}
		}
		order.setNoOfItems(noOfItems);
		order.setTotalValue(totalValue);
		order.setTotalCost(totalCost);
		order.setTotalTax(totalValue - totalCost);
		orderRepo.save(order);
	}

	private double computeValueForItem(long quantity, ProductCategory productCategory, double rate) {
		logger.debug("productCategory : "+productCategory+"  quantity = "+quantity+"  rate = "+rate);
		double saleValue=0;
		if (productCategory.equals(ProductCategory.A)){
			saleValue = quantity * rate * 1.1;   //10% levy
			
		}else if (productCategory.equals(ProductCategory.B)){
			saleValue = quantity * rate * 1.2;   //10% levy
			
		}else if (productCategory.equals(ProductCategory.C)){
			saleValue = quantity * rate;  
		}
		return saleValue;
	}

	private Order addProductToOrder(Long orderId, String barCodeId, int quantity) {
		Order o1 = orderRepo.findOne(orderId);
		Product selectedProduct1 = verifyIfProductExists(barCodeId);
		
		// create line item for a product 
		LineItem l1 = new LineItem(selectedProduct1, quantity);
		lineItemRepo.save(l1);
		//logger.debug("saved line item  = " + l1.getId());
		
		// add lineitem to order.
		List<LineItem> currentLineItems = o1.getLineItems();
		if (currentLineItems != null) { // There are lineItems in the order already.
			logger.debug("There are lineItems in the order already..Adding to list of items");
			LineItem existingLi =getLineItemWithBarCodeId(barCodeId, currentLineItems);
			if (existingLi==null){ 
			//	logger.debug("There is no lineItem for the product");
				o1.getLineItems().add(l1); //there is no line item with existing product
			}else{
				long newQty= existingLi.getQuantity()+quantity;
				//logger.debug("Product already exists in the order. Adding to the quantity. New qty = "+newQty);
				existingLi.setQuantity(newQty); // increment the quantity of the product if it already exists in the Order.
			}
			
		} else {
			logger.debug("There are no line items currently in the Order..Creating new list");
			currentLineItems = new ArrayList<LineItem>();
			currentLineItems.add(l1);
			o1.setLineItems(currentLineItems);
		}
		orderRepo.save(o1);
		logger.debug("Product Added Successfully  to Order : " + l1.getId());
		return o1;
	}
	
	
	private Order removeProductFromOrder(Long orderId, String barCodeId) {
		Order o1 = orderRepo.findOne(orderId);
		List<LineItem> currentLineItems = o1.getLineItems();
		//check if the product exists in product master
		verifyIfProductExists(barCodeId);
		logger.info("Bar Code Id to be removed  = " + barCodeId);

		if (currentLineItems != null && currentLineItems.size()!=0) {
			LineItem lineItem = getLineItemWithBarCodeId(barCodeId,currentLineItems);
			//check if current list of line items have this product.
			if (null==lineItem){
				logger.info("Product does not exist in current list of products. Cannot remove productId : "+barCodeId);
				throw new CustomException("Problem with input data: Product does not exist in current list of products. Cannot remove product with BarCode ID " + barCodeId );

			}
			logger.info("line item to be deleted "+lineItem);
			currentLineItems.remove(lineItem);
			o1.setLineItems(currentLineItems);
			orderRepo.save(o1);
			//lineItemRepo.delete(lineItem);  //delete if it exists
		} else {
			logger.info("There are no line items currently in the Order..Cannot remove productId : "+barCodeId);
			throw new CustomException("Problem with input data: There are no line items currently in the Order. Cannot remove product with BarCode ID " + barCodeId );
		}
		orderRepo.save(o1);
		return o1;
	}

	// Iterate through line items to get the LineItem with product.
	private LineItem getLineItemWithBarCodeId(String barCodeId, List<LineItem> currentLineItems) {
		for (int i=0;i<currentLineItems.size();i++){
			LineItem li = (LineItem) currentLineItems.get(i);
			if (barCodeId.equals(li.getProduct().getBarCodeId())){
				//assumes there will only be one item per product. Save method to ensure that there are no duplicates.
				logger.info(" Line Items has product: " + barCodeId);
				return li;
			}
		}
		logger.info(" Current list of Line Items do not have product: " + barCodeId);
		return null;
	}

	private Product verifyIfProductExists(String barCodeId) {
		List<Product> productsByBarCodeID = productRepo.findByBarCodeId(barCodeId);
		if (null== productsByBarCodeID  || productsByBarCodeID.size()==0) {
			logger.info("Problem with input data: BarCode ID  " + barCodeId + " does not exist in Product Master");
			throw new CustomException(
					"Problem with input data: BarCode ID " + barCodeId + " does not exist in Product Master");
		} else{
			logger.debug("selectedProduct1  = " + productsByBarCodeID.get(0).getId());	
		}
		return productsByBarCodeID.get(0);
	}

	public void deleteOrder(Long id) {
		verifyOrderExists(id);
		orderRepo.delete(id);
	}

}
