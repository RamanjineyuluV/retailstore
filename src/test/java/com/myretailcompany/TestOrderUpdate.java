package com.myretailcompany;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.myretailcompany.rest.controller.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.beans.ProductInfo;
import com.myretailcompany.util.OrderStatus;

@RunWith(SpringRunner.class)
@JsonTest
public class TestOrderUpdate {
	
	@Autowired
    private JacksonTester<OrderUpdateInfo> json;

    @Test
    public void testSerialize() throws Exception {
    	OrderUpdateInfo updateInfo = new OrderUpdateInfo();
    	
    	updateInfo.setStatus(OrderStatus.COMPLETED);
    	List<ProductInfo> productsToBeAdded = new ArrayList<ProductInfo>();
    	productsToBeAdded.add(new ProductInfo(1,20));
    	productsToBeAdded.add(new ProductInfo(2,30));
    	productsToBeAdded.add(new ProductInfo(3l,10));
    	
    	List<ProductInfo> productsToBeRemoved=new ArrayList<ProductInfo>();
    	productsToBeRemoved.add(new ProductInfo(11,2));
    	productsToBeRemoved.add(new ProductInfo(12,3));
    	productsToBeRemoved.add(new ProductInfo(13,1));
    	
		updateInfo.setProductsToBeAdded(productsToBeAdded);
		updateInfo.setProductsToBeRemoved(productsToBeRemoved);
		
		assertThat(this.json.write(updateInfo)).isEqualToJson("expected.json");
		
		System.out.println(this.json.write(updateInfo).toString());
    	
       
    }
	
	

}
