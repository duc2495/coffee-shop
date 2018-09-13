package coffeeshop.resource.dashboard;

import java.util.ArrayList;
import java.util.List;

import coffeeshop.resource.order.OrderListResource;
import coffeeshop.resource.product.BestProductResource;
import coffeeshop.resource.product.ProductDetailResource;
import lombok.Data;

@Data
public class DashboardResource {
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

	private BestProductResource bestProduct;

	private List<OrderListResource> latestOrders;
	private List<ProductDetailResource> newProducts;

	private ArrayList<IncomeChart> listIncome;
	private ArrayList<ProductChart> listProduct;
}
