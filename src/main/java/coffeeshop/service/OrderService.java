package coffeeshop.service;

import java.util.List;

import coffeeshop.controller.order.OrderResource;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;

public interface OrderService {

	public void insertOrder(OrderResource orderResource);
	public List<Order> getAllOrder();
	public List<OrderProduct> getAllOrderProduct(Order order);
}
