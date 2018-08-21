package coffeeshop.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.product.Product;

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
	public boolean hasOrder(Integer orderId);
	public void updateOrderStatus(Order order);
	public List<Order> getAllActiveOrderHaveProduct(Product product);
	public List<Order> getNewOrderInTimeInterval(@Param("dayFrom") Date dayFrom,@Param("dayTo") Date dayTo);
}
