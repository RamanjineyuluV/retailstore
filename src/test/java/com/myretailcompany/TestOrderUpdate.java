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

import com.myretailcompany.rest.controller.order.beans.OrderUpdateInfo;
import com.myretailcompany.rest.controller.order.beans.ProductInfoForOrder;
import com.myretailcompany.util.OrderStatus;

@RunWith(SpringRunner.class)
@JsonTest
public class TestOrderUpdate {
	
	@Autowired
    private JacksonTester<OrderUpdateInfo> json;

    @Test
    public void testSerialize() throws Exception {

    	OrderUpdateInfo updateInfo = createTestBean();
		
		assertThat(this.json.write(updateInfo)).isEqualToJson("expected.json");
		
		System.out.println(this.json.write(updateInfo).toString());
    }

	public OrderUpdateInfo createTestBean() {
		OrderUpdateInfo updateInfo = new OrderUpdateInfo();
    	updateInfo.setStatus(OrderStatus.IN_PROGRESS);
    	List<ProductInfoForOrder> productsToBeAdded = new ArrayList<ProductInfoForOrder>();
    	productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-1234",20));
    	productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-2234",30));
    	productsToBeAdded.add(new ProductInfoForOrder("ABC-abc-3234",10));
    	
    	List<ProductInfoForOrder> productsToBeRemoved=new ArrayList<ProductInfoForOrder>();
    	productsToBeRemoved.add(new ProductInfoForOrder("ABC-abc-1235",2));
    	productsToBeRemoved.add(new ProductInfoForOrder("ABC-abc-1236",3));
    	productsToBeRemoved.add(new ProductInfoForOrder("ABC-abc-1237",1));
    	
		updateInfo.setProductsToBeAdded(productsToBeAdded);
		updateInfo.setProductsToBeRemoved(productsToBeRemoved);
		return updateInfo;
	}

   
}
