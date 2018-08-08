package coffeeshop.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;

/**
 * @author hoang
 *　Mybatisが自動的に作るDAOクラスのインターフェース
 */
@Repository
public interface OrderRepository {
	public List<Order> getAllOrder();
	public Order findOrderById(Integer id);
	public List<Order> findOrderByPhone(String phone);
	public List<Order> findOrderByCustomerName(String name);
	public Integer insertOrder(@Param("order") Order order);
	public Integer insertOrderProduct(@Param("order") Order order, @Param("orderProduct") OrderProduct orderProduct);
	public void updateOrder(Order order);
}
