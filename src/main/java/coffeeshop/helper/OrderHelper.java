package coffeeshop.helper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.product.Product;
import coffeeshop.resource.order.OrderUpdateResource;
import coffeeshop.resource.order.OrderDetailResource;
import coffeeshop.resource.order.OrderResource;
import coffeeshop.resource.order.OrderProductDetailResource;
import coffeeshop.resource.order.OrderProductResource;
import coffeeshop.resource.order.OrderRequestResource;

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
	public Order createOrderModel(OrderRequestResource resource) {
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

	public OrderResource createOrderListResource(Order model) {
		return modelMapper.map(model, OrderResource.class);
	}

	public Order createOrderModel(OrderUpdateResource resource) {
		return modelMapper.map(resource, Order.class);
	}
}
