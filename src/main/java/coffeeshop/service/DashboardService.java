package coffeeshop.service;

import java.util.Date;
import java.util.List;

import coffeeshop.resource.dashboard.DashboardResource;
import coffeeshop.resource.order.OrderListResource;
import coffeeshop.resource.product.ProductDetailResource;

public interface DashboardService {
	public DashboardResource getDashboardResource(Date dayFrom, Date dayTo);
	public List<OrderListResource> getTopTenLastestOrder();
	public List<ProductDetailResource> getTopTenLastestProduct();
}
