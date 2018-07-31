package coffeeshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.net.SyslogOutputStream;
import coffeeshop.controller.order.OrderHelper;
import coffeeshop.controller.order.OrderResource;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.repository.OrderRepository;
import coffeeshop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderHelper orderHelper;
	
	public Integer insertOrder(Order order) {
		return orderRepository.insertOrder(order);
		
	}

	public void insertOrderProduct(Order order, OrderProduct orderProduct) {
		orderRepository.insertOrderProduct(order, orderProduct);
	}

	@Override
	public List<Order> getAllOrder() {
		
		return orderRepository.getAllOrder();
	}

	@Override
	public List<OrderProduct> getAllOrderProduct(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=false)
	public void insertOrder(OrderResource orderResource) {
		Order order = orderHelper.createOrderModel(orderResource);
		order.setStatus(OrderStatus.ORDERED);
		System.out.println(order.getCustomerAddress()+order.getCustomerName()+order.getCustomerPhone()+order.getNetPrice());
		order.getOrderProductList().forEach(e->System.out.println(e.getProduct().getProductName()+":"+e.getQuantity()));
		this.insertOrder(order);
		for(OrderProduct orderProduct : order.getOrderProductList()){
			insertOrderProduct(order, orderProduct);
		}
	}

}
