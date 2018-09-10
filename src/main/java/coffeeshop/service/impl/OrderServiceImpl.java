package coffeeshop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.repository.OrderRepository;
import coffeeshop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see coffeeshop.service.OrderService#getAllOrder()
	 */
	@Override
	public List<Order> getAllOrder() {
		return orderRepository.getAllOrder();
	}

	@Transactional(readOnly = false)
	public int insertOrder(Order order) {
		orderRepository.insertOrder(order);
		orderRepository.insertOrderProductList(order.getOrderId(), order.getOrderProductList());
		return order.getOrderId();
	}

	public Order findOrderById(Integer id) {
		Order order = orderRepository.findOrderById(id);
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * coffeeshop.service.OrderService#updateOrder(coffeeshop.model.order.Order)
	 */
	@Override
	public void updateOrder(Order order) {
		orderRepository.updateOrder(order);
	}

	@Override
	public boolean hasOrder(Integer orderId) {
		return orderRepository.hasOrder(orderId);
	}

	@Override
	public void updateOrderStatus(Order order) {
		orderRepository.updateOrderStatus(order);
	}

	@Override
	public boolean checkIfProductIsInActiveOrder(Product product) {

		return !orderRepository.getAllActiveOrderHaveProduct(product).isEmpty();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> getNewOrderInTimeInterval(Date timeFrom, Date timeTo) {
		System.out.println(timeTo);
		return orderRepository.getNewOrderInTimeInterval(timeFrom, timeTo);
	}

	public Integer getNumberOfOrderByStatus(List<Order> orderList, OrderStatus status) {
		return orderList.stream().filter(new Predicate<Order>() {

			@Override
			public boolean test(Order t) {
				return t.getStatus().equals(status);
			}

		}).collect(Collectors.toList()).size();
	}

}
