package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.service.product.ProductService;

@Component
public class OrderHelper {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProductService productService;
	/**
	 * 商品のresource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public Order createOrderModel(OrderResource resource) {
		// 商品情報をマッピング
		Order model = modelMapper.map(resource, Order.class);
		Integer net_price = 0;
		List<OrderProduct> orderProductList = new LinkedList<OrderProduct>();
		for (OrderProductResource op : resource.getOrderList()){
			OrderProduct orderProduct = createOrderProductModel(op);
			orderProductList.add(orderProduct);
			net_price+=orderProduct.getProduct().getPrice()*orderProduct.getQuantity();
		}
		model.setNetPrice(net_price);
		model.setOrderProductList(orderProductList);
		return model;
	}
	
	/**
	 * 商品のupdateResource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public OrderProduct createOrderProductModel(OrderProductResource resource) {
		// 商品情報をマッピング
		OrderProduct model = modelMapper.map(resource, OrderProduct.class);
		model.setProduct(productService.getProductDetail(Integer.parseInt(resource.getProductId())));
		return model;
	}
	
	public OrderProductDetailResource createOrderProductDetailResource(OrderProduct model){
		OrderProductDetailResource opdr = modelMapper.map(model, OrderProductDetailResource.class);
		opdr.setProductId(model.getProduct().getProductId());
		opdr.setPrice(model.getProduct().getPrice()*opdr.getQuantity());
		opdr.setProductName(model.getProduct().getProductName());
		return opdr;
	}
	
	public OrderDetailResource createOrderDetailResource(Order model){
		OrderDetailResource orderDetailResource = modelMapper.map(model, OrderDetailResource.class);
		List<OrderProductDetailResource> orderProductDetailList = new LinkedList<OrderProductDetailResource>();
		for(OrderProduct op : model.getOrderProductList()){
			orderProductDetailList.add(createOrderProductDetailResource(op));
		}
		System.out.println(model.getOrderProductList().size());
		orderDetailResource.setOrderProductDetailList(orderProductDetailList);
		return orderDetailResource;
		
	}
}
