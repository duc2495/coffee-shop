package coffeeshop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coffeeshop.helper.OrderHelper;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.repository.OrderRepository;
import coffeeshop.resource.order.OrderProductResource;
import coffeeshop.resource.order.OrderResource;
import coffeeshop.service.OrderService;
import coffeeshop.service.product.ProductService;

/**
 * @author hoang
 * 
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderHelper orderHelper;

	@Autowired
	private ProductService productService;

	public Integer insertOrder(Order order) {
		return orderRepository.insertOrder(order);

	}

	/**
	 * データベースにオーダープロダクトを追加
	 * 
	 * @param order
	 * @param orderProduct
	 */
	public void insertOrderProduct(Order order, OrderProduct orderProduct) {
		orderRepository.insertOrderProduct(order, orderProduct);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see coffeeshop.service.OrderService#getAllOrder()
	 */
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
	@Transactional(readOnly = false)
	public int insertOrder(OrderResource orderResource) {
		Order order = orderHelper.createOrderModel(orderResource);
		order.setStatus(OrderStatus.ORDERED);
		int total_check = 0;
		for (OrderProductResource opdr : orderResource.getOrderProductList()) {
			total_check += opdr.getQuantity()
					* (productService.getProductDetail(opdr.getProduct().getProductId()).getPrice());
		}
		order.setNetPrice(total_check);
		this.insertOrder(order);
		for (OrderProduct orderProduct : order.getOrderProductList()) {
			orderProduct.setProduct(productService.getProductDetail(orderProduct.getProduct().getProductId()));
			insertOrderProduct(order, orderProduct);
		}
		return order.getOrderId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see coffeeshop.service.OrderService#findOrderById(java.lang.Integer)
	 */
	@Override
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
