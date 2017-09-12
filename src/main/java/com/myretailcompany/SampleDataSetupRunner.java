package com.myretailcompany;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myretailcompany.dataaccesslayer.entity.LineItem;
import com.myretailcompany.dataaccesslayer.entity.Order;
import com.myretailcompany.dataaccesslayer.entity.Product;
import com.myretailcompany.dataaccesslayer.repository.LineItemRepository;
import com.myretailcompany.dataaccesslayer.repository.OrderRepository;
import com.myretailcompany.dataaccesslayer.repository.ProductRepository;
import com.myretailcompany.util.OrderStatus;
import com.myretailcompany.util.ProductCategory;

@Component
public class SampleDataSetupRunner implements CommandLineRunner {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductRepository productMasterRepo;

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private LineItemRepository lineItemRepo;
	
	
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Inside Runner..");
		setUpProductMasterData();
		logger.info("Exiting Runner.. ");
	}

	private void setUpProductMasterData() {
		
		// ADD Products to master
		logger.info("Inside setUpProductMasterData..");
		productMasterRepo.save(new Product("ABC-abc-1234", 20.0, "Tomato", ProductCategory.A));
		productMasterRepo.save(new Product("ABC-abc-1235",  40.0, "Onion", ProductCategory.B));
		productMasterRepo.save(new Product("ABC-abc-1236", 50.0, "Potato", ProductCategory.C));
		productMasterRepo.save(new Product("ABC-abc-1237",  35.0, "Bread", ProductCategory.A));
		productMasterRepo.save(new Product("ABC-abc-1238",  100.0, "Apples", ProductCategory.B));
		productMasterRepo.save(new Product("ABC-abc-1239", 49.0, "Banana", ProductCategory.C));
		logger.info("Completed  setUpProductMasterData..Count of records = "+productMasterRepo.count());
		
		//create an order
		Order o1 = new Order(0.0,0,OrderStatus.CREATED);
		orderRepo.save(o1);
		logger.info("order saved = "+o1.getId());
		
		//create line item for a product and add to order list.
		List<LineItem> lineItems = new ArrayList<LineItem>();
		Product selectedProduct1 = productMasterRepo.findByBarCodeId("ABC-abc-1235");
		logger.info("selectedProduct1  = "+selectedProduct1.getId());
		LineItem l1 = new LineItem(selectedProduct1,1);
		lineItemRepo.save(l1);
		logger.info("saved line item  = "+l1.getId());
		
		if (selectedProduct1!=null){
			
			List<LineItem> currentLineItems = o1.getLineItems();
			if (currentLineItems!=null){ 
				logger.info("Adding to list of items");
				o1.getLineItems().add(l1); 
			}else{
				logger.info("There are no line items added to order");
				currentLineItems = new ArrayList<LineItem>();
				currentLineItems.add(l1);
				o1.setLineItems(currentLineItems);
			}
			
			orderRepo.save(o1);
		} else{
			logger.info("No product found to add");
		}
		
		// BeanUtils.copyProperties(reco1, reco2);
	}

}
