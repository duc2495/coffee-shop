package coffeeshop.service;

import java.util.List;

import coffeeshop.controller.order.OrderResource;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;

public interface OrderService {

	public int insertOrder(OrderResource orderResource);
	public Order findOrderById(Integer id);
	public List<Order> getAllOrder();
	public List<OrderProduct> getAllOrderProduct(Order order);
}
