package coffeeshop.service;

import java.util.Date;
import java.util.List;

import coffeeshop.model.order.Order;
import coffeeshop.model.product.Product;

/**
 * @author hoang
 *
 */
public interface OrderService {

	/**
	 * データベースに新リソースを追加
	 * @param orderResource　オーダーリソース
	 * @return　新オーダーのID
	 */
	public int insertOrder(Order order);
	
	/**
	 * IDでオーダーを検索
	 * @param id
	 * @return　IDがあるオーダー、またはNULL
	 */
	public Order findOrderById(Integer id);
	
	/**
	 * すべてのオーダーを取る
	 * @return　すべてのオーダー
	 */
	public List<Order> getAllOrder();
	
	/**
	 * オーダーを変更するサービス
	 * @param order
	 */
	public void updateOrder(Order order);
	
	public boolean hasOrder(Integer orderId);
	
	public void updateOrderStatus(Order order);
	
	public boolean checkIfProductIsInActiveOrder(Product product);
	
	public List<Order> getNewOrderInTimeInterval(Date timeFrom, Date timeTo);
	
	public List<Order> getNewOrders();
}
