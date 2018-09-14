package coffeeshop.model.dashboard;

import java.util.ArrayList;
import java.util.List;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.product.Product;
import lombok.Data;

@Data
public class Dashboard {
	private int newOrderNumber;
	private int newProductNumber;
	private int income;
	private int highestPriceOrderId;
	private int orderedNumber;
	private int shippingNumber;
	private int finishedNumber;
	private int canceledNumber;
	private int totalProducts;
	private int pureCoffee;
	private int fromCoffee;
	private int noneCoffee;

	private OrderProduct bestProduct;

	private List<Order> latestOrders;
	private List<Product> newProducts;

	private ArrayList<IncomeChart> listIncome;
	private ArrayList<ProductChart> listProduct;
}
