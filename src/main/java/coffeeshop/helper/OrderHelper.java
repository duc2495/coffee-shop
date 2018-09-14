package coffeeshop.helper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.product.Product;
import coffeeshop.resource.order.AdminOrderUpdateResource;
import coffeeshop.resource.order.OrderDetailResource;
import coffeeshop.resource.order.OrderListResource;
import coffeeshop.resource.order.OrderProductDetailResource;
import coffeeshop.resource.order.OrderProductResource;
import coffeeshop.resource.order.OrderResource;

@Component
public class OrderHelper {
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 商品のresource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public Order createOrderModel(OrderResource resource) {
		// 商品情報をマッピング
		Order order = modelMapper.map(resource, Order.class);
		order.setOrderProductList(resource.getOrderProductList().stream().map(e -> createOrderProductModel(e))
				.collect(Collectors.toList()));
		System.out.println(order.getOrderProductList().size());
		return order;
	}

	/**
	 * 商品のupdateResource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public OrderProduct createOrderProductModel(OrderProductResource resource) {
		// 商品情報をマッピング
		return modelMapper.map(resource, OrderProduct.class);
	}

	public OrderProduct createOrderProductModel(OrderProductDetailResource resource) {
		OrderProduct orderProduct = modelMapper.map(resource, OrderProduct.class);
		Product product = new Product();
		product.setProductId(resource.getProductId());
		orderProduct.setProduct(product);
		return orderProduct;
	}

	public OrderProductDetailResource createOrderProductDetailResource(OrderProduct model) {
		return modelMapper.map(model, OrderProductDetailResource.class);
	}

	public OrderDetailResource createOrderDetailResource(Order model) {
		return modelMapper.map(model, OrderDetailResource.class);

	}

	public OrderListResource createOrderListResource(Order model) {
		return modelMapper.map(model, OrderListResource.class);
	}

	public Order createOrderModel(AdminOrderUpdateResource resource) {
		return modelMapper.map(resource, Order.class);
	}
}
