package coffeeshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.dashboard.Dashboard;
import coffeeshop.model.dashboard.IncomeChart;
import coffeeshop.model.dashboard.ProductChart;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.service.DashboardService;
import coffeeshop.service.OrderService;
import coffeeshop.service.product.ProductService;

@Component
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	public Dashboard getDashboard(Date dayFrom, Date dayTo) {
		Dashboard dashboard = new Dashboard();
		Calendar calFrom = Calendar.getInstance();
		calFrom.setTime(dayFrom);
		calFrom.set(Calendar.HOUR_OF_DAY, 0);
		calFrom.set(Calendar.MINUTE, 0);
		calFrom.set(Calendar.SECOND, 0);
		dayFrom = calFrom.getTime();
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(dayTo);
		calTo.set(Calendar.HOUR, 23);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		dayTo = calTo.getTime();
		List<Order> orderList = orderService.getNewOrderInTimeInterval(dayFrom, dayTo);
		List<Product> newProductList = productService.getNewProductInTimeInterval(dayFrom, dayTo);
		ArrayList<ProductChart> productList = getProductNumberListSoldInType(dayTo);
		ArrayList<IncomeChart> incomeList = getIncomeList(dayTo);

		dashboard.setOrderedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.ORDERED));
		dashboard.setShippingNumber(getNumberOfOrderInStatus(orderList, OrderStatus.SHIPPING));
		dashboard.setFinishedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.FINISHED));
		dashboard.setCanceledNumber(getNumberOfOrderInStatus(orderList, OrderStatus.CANCELED));

		dashboard.setPureCoffee(getNumberOfProductInType(orderList, ProductType.PURE_COFFEE));
		dashboard.setFromCoffee(getNumberOfProductInType(orderList, ProductType.FROM_COFFEE));
		dashboard.setNoneCoffee(getNumberOfProductInType(orderList, ProductType.NON_COFFEE));
		dashboard.setTotalProducts(dashboard.getPureCoffee() + dashboard.getFromCoffee() + dashboard.getNoneCoffee());

		dashboard.setListIncome(incomeList);
		dashboard.setListProduct(productList);
		dashboard.setBestProduct(getBestSoldProduct(orderList));
		dashboard.setIncome(getIncome(orderList, dayFrom, dayTo));
		dashboard.setNewProductNumber(newProductList.size());
		dashboard.setNewOrderNumber(orderList.size());
		dashboard.setHighestPriceOrderId(getHighestPriceOrderId(orderList));
		return dashboard;
	}

	/**
	 * @param orderList
	 * @param status
	 * @return orderListのstatusになるオーダーの数
	 */
	private Integer getNumberOfOrderInStatus(List<Order> orderList, OrderStatus status) {
		return orderList.stream().filter(o -> {
			return o.getStatus().equals(status);
		}).collect(Collectors.toList()).size();
	}

	private Integer getNumberOfProductInType(List<Order> orderList, ProductType type) {
		return orderList.stream().map(e -> e.getOrderProductList().stream().filter(p -> p.getProductType().equals(type))
				.collect(Collectors.toList()).size()).collect(Collectors.summingInt(e -> e));
	}

	/**
	 * @param dayTo
	 * @return 一年間の収入のリスト
	 */
	private ArrayList<IncomeChart> getIncomeList(Date dayTo) {
		Calendar dayFrom = Calendar.getInstance();
		dayFrom.add(Calendar.MONTH, -12);
		Calendar lastDay = Calendar.getInstance();
		lastDay.set(Calendar.DATE, lastDay.getActualMaximum(Calendar.DATE));
		ArrayList<IncomeChart> incomeList = new ArrayList<IncomeChart>();
		List<Order> orderList = orderService.getNewOrderInTimeInterval(dayFrom.getTime(), lastDay.getTime());
		for (int i = -11; i <= 0; i++) {
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(dayTo);
			Calendar lastDate = Calendar.getInstance();
			lastDate.setTime(dayTo);
			firstDate.set(Calendar.HOUR_OF_DAY, 0);
			firstDate.set(Calendar.MINUTE, 0);
			firstDate.set(Calendar.SECOND, 0);
			firstDate.add(Calendar.MONTH, i);
			firstDate.set(Calendar.DATE, firstDate.getActualMinimum(Calendar.DATE));
			lastDate.add(Calendar.MONTH, i);
			lastDate.set(Calendar.DATE, lastDate.getActualMaximum(Calendar.DATE));
			Integer result = getIncome(orderList, firstDate.getTime(), lastDate.getTime());
			incomeList.add(new IncomeChart(firstDate.getTime(), result.intValue()));
		}
		return incomeList;
	}

	/**
	 * @param orderList
	 * @param dayFrom
	 * @param dayTo
	 * @return dayFromからdayToまでの収入
	 */
	private Integer getIncome(List<Order> orderList, Date dayFrom, Date dayTo) {
		return orderList.stream()
				.filter(o -> o.getStatus().equals(OrderStatus.FINISHED) && !o.getCreatedAt().before(dayFrom)
						&& !o.getCreatedAt().after(dayTo))
				.map(e -> e.getOrderProductList().stream()
						.collect(Collectors.summingInt(q -> q.getQuantity() * q.getPrice())))
				.collect(Collectors.summingInt(i -> i));
	}

	/**
	 * @param orderList
	 * @param type
	 * @param dayFrom
	 * @param dayTo
	 * @return dayFromからdayToまで売ったtypeの製品
	 */
	private Integer getProductNumberSoldInType(List<Order> orderList, ProductType type, Date dayFrom, Date dayTo) {
		return orderList.stream()
				.filter(o -> o.getStatus().equals(OrderStatus.FINISHED) && !o.getCreatedAt().before(dayFrom)
						&& !o.getCreatedAt().after(dayTo))
				.map(e -> e.getOrderProductList().stream().filter(op -> op.getProductType().equals(type))
						.collect(Collectors.summingInt(m -> m.getQuantity())))
				.collect(Collectors.summingInt(c -> c));
	}

	/**
	 * @param dayTo
	 * @return dayToまで、一年間の売った製品の数
	 */
	private ArrayList<ProductChart> getProductNumberListSoldInType(Date dayTo) {
		Calendar dayFrom = Calendar.getInstance();
		dayFrom.add(Calendar.MONTH, -12);
		Calendar lastDay = Calendar.getInstance();
		lastDay.set(Calendar.DATE, lastDay.getActualMaximum(Calendar.DATE));
		ArrayList<ProductChart> productList = new ArrayList<ProductChart>();
		List<Order> orderList = orderService.getNewOrderInTimeInterval(dayFrom.getTime(), lastDay.getTime());
		for (int i = -11; i <= 0; i++) {
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(dayTo);
			Calendar lastDate = Calendar.getInstance();
			lastDate.setTime(dayTo);
			firstDate.add(Calendar.MONTH, i);
			firstDate.set(Calendar.DATE, firstDate.getActualMinimum(Calendar.DATE));
			lastDate.add(Calendar.MONTH, i);
			lastDate.set(Calendar.DATE, lastDate.getActualMaximum(Calendar.DATE));
			Integer from_coffee = getProductNumberSoldInType(orderList, ProductType.FROM_COFFEE, firstDate.getTime(),
					lastDate.getTime());
			Integer pure_coffee = getProductNumberSoldInType(orderList, ProductType.PURE_COFFEE, firstDate.getTime(),
					lastDate.getTime());
			Integer non_coffee = getProductNumberSoldInType(orderList, ProductType.NON_COFFEE, firstDate.getTime(),
					lastDate.getTime());
			productList.add(new ProductChart(firstDate.getTime(), pure_coffee, from_coffee, non_coffee));
		}
		return productList;
	}

	private OrderProduct getBestSoldProduct(List<Order> orderList) {
		HashMap<Integer, Integer> map = (HashMap<Integer, Integer>) orderList.stream()
				.filter(od -> od.getStatus().equals(OrderStatus.FINISHED))
				.flatMap(o -> o.getOrderProductList().stream()).collect(Collectors.toList()).stream()
				.collect(Collectors.toMap(p -> p.getProduct().getProductId(), OrderProduct::getQuantity,
						(oldVal, newVal) -> (oldVal + newVal)));
		if (map.entrySet().isEmpty()) {
			return new OrderProduct();
		}

		Integer bestProductId = map.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		Product product = productService.getProductDetail(bestProductId);
		OrderProduct bestProduct = new OrderProduct();
		bestProduct.setProduct(product);
		bestProduct.setProductName(product.getProductName());
		bestProduct.setQuantity(map.get(bestProductId));
		return bestProduct;
	}

	public List<Order> getNewOrders() {
		List<Order> orderList = orderService.getNewOrders();
		return orderList;
	}

	public List<Product> getNewProducts() {
		List<Product> productList = productService.getNewProducts();
		return productList;
	}

	public Integer getHighestPriceOrderId(List<Order> orderList) {
		orderList.sort((order1, order2) -> order1.getNetPrice() > order2.getNetPrice() ? -1 : 1);
		if (orderList.isEmpty())
			return 0;
		return orderList.get(0).getOrderId();
	}
}
