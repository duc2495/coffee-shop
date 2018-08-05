package coffeeshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coffeeshop.controller.order.OrderHelper;
import coffeeshop.controller.order.OrderResource;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.repository.OrderRepository;
import coffeeshop.service.OrderService;

/**
 * @author hoang
 * 
 */
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

	/**
	 * データベースにオーダープロダクトを追加
	 * @param order
	 * @param orderProduct
	 */
	public void insertOrderProduct(Order order, OrderProduct orderProduct) {
		orderRepository.insertOrderProduct(order, orderProduct);
	}

	/* (non-Javadoc)
	 * @see coffeeshop.service.OrderService#getAllOrder()
	 */
	@Override
	public List<Order> getAllOrder() {
		return orderRepository.getAllOrder();
	}

	
	/* (non-Javadoc)
	 * @see coffeeshop.service.OrderService#getAllOrderProduct(coffeeshop.model.order.Order)
	 */
	@Override
	public List<OrderProduct> getAllOrderProduct(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see coffeeshop.service.OrderService#insertOrder(coffeeshop.controller.order.OrderResource)
	 */
	@Override
	@Transactional(readOnly=false)
	public int insertOrder(OrderResource orderResource) {
		Order order = orderHelper.createOrderModel(orderResource);
		order.setStatus(OrderStatus.ORDERED);
		this.insertOrder(order);
		for(OrderProduct orderProduct : order.getOrderProductList()){
			insertOrderProduct(order, orderProduct);
		}
		return order.getOrderId();
	}

	
	/* (non-Javadoc)
	 * @see coffeeshop.service.OrderService#findOrderById(java.lang.Integer)
	 */
	@Override
	public Order findOrderById(Integer id) {
		Order order = orderRepository.findOrderById(id);
		return order;
	}

}
