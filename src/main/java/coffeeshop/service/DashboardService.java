package coffeeshop.service;

import java.util.Date;
import java.util.List;

import coffeeshop.model.dashboard.Dashboard;
import coffeeshop.model.order.Order;
import coffeeshop.model.product.Product;

public interface DashboardService {
	public Dashboard getDashboard(Date dayFrom, Date dayTo);
	public List<Order> getNewOrders();
	public List<Product> getNewProducts();
}
