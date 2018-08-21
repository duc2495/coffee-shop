package coffeeshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit4.SpringRunner;

import coffeeshop.model.order.Order;
import coffeeshop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	public void getNewOrderInTimeIntervalTest(){
		Calendar carlendar1 = new GregorianCalendar();
    	Calendar carlendar2 = new GregorianCalendar();
    	carlendar2.set(2018, 7, 15);
    	Date timeTo = carlendar1.getTime();
    	Date timeFrom = carlendar2.getTime();
    	List<Order> orderList = orderRepository.getNewOrderInTimeInterval(timeFrom, timeTo);
    	assertEquals(9, orderList.size());
    	for(Order o : orderList){
    		assertNotEquals(0,o.getOrderProductList().size());
    	}
    	assertEquals(0,orderRepository.getNewOrderInTimeInterval(timeTo, timeTo).size());
	}
}
