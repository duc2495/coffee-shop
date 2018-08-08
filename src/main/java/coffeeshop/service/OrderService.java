package coffeeshop.service;

import java.util.List;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.resource.order.OrderResource;

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
	public int insertOrder(OrderResource orderResource);
	
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
	
	public List<OrderProduct> getAllOrderProduct(Order order);
	
	/**
	 * オーダーを変更するサービス
	 * @param order
	 */
	public void updateOrder(Order order);
}
