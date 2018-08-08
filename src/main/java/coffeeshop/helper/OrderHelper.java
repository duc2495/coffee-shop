package coffeeshop.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.resource.order.OrderDetailResource;
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
		return modelMapper.map(resource, Order.class);
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
	
	public OrderProductDetailResource createOrderProductDetailResource(OrderProduct model){
		return modelMapper.map(model, OrderProductDetailResource.class);
	}
	
	public OrderDetailResource createOrderDetailResource(Order model){
		return modelMapper.map(model, OrderDetailResource.class);
		
	}
}
