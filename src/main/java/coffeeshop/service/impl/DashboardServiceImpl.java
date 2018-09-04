package coffeeshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.helper.OrderHelper;
import coffeeshop.helper.ProductHelper;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.resource.dashboard.DashboardResource;
import coffeeshop.resource.dashboard.IncomeChart;
import coffeeshop.resource.dashboard.ProductChart;
import coffeeshop.resource.order.OrderListResource;
import coffeeshop.resource.product.BestProductResource;
import coffeeshop.resource.product.ProductDetailResource;
import coffeeshop.service.DashboardService;
import coffeeshop.service.OrderService;
import coffeeshop.service.product.ProductService;

@Component
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductHelper productHelper;
	@Autowired
	private OrderHelper orderHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see coffeeshop.service.DashboardService#getDashboardResource(java.util.Date,
	 * java.util.Date)
	 */
	@Override
	public DashboardResource getDashboardResource(Date dayFrom, Date dayTo) {
		DashboardResource resource = new DashboardResource();
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

		resource.setOrderedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.ORDERED));
		resource.setShippingNumber(getNumberOfOrderInStatus(orderList, OrderStatus.SHIPPING));
		resource.setFinishedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.FINISHED));
		resource.setCanceledNumber(getNumberOfOrderInStatus(orderList, OrderStatus.CANCELED));

		resource.setPureCoffee(getNumberOfProductInType(orderList, ProductType.PURE_COFFEE));
		resource.setFromCoffee(getNumberOfProductInType(orderList, ProductType.FROM_COFFEE));
		resource.setNoneCoffee(getNumberOfProductInType(orderList, ProductType.NON_COFFEE));
		resource.setTotalProducts(resource.getPureCoffee() + resource.getFromCoffee() + resource.getNoneCoffee());

		resource.setListIncome(incomeList);
		resource.setListProduct(productList);
		resource.setBestProduct(getBestSoldProduct(orderList));
		resource.setIncome(getIncome(orderList, dayFrom, dayTo));
		resource.setNewProductNumber(newProductList.size());
		resource.setNewOrderNumber(orderList.size());
		resource.setHighestPriceOrderId(getHighestPriceOrderId(orderList));
		return resource;
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

	private BestProductResource getBestSoldProduct(List<Order> orderList) {
		HashMap<Integer, Integer> map = (HashMap<Integer, Integer>) orderList.stream()
				.filter(od -> od.getStatus().equals(OrderStatus.FINISHED))
				.flatMap(o -> o.getOrderProductList().stream()).collect(Collectors.toList()).stream()
				.collect(Collectors.toMap(p -> p.getProduct().getProductId(), OrderProduct::getQuantity,
						(oldVal, newVal) -> (oldVal + newVal)));
		if (map.entrySet().isEmpty()) {
			return new BestProductResource();
		}

		Integer bestProductId = map.entrySet().stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		Product product = productService.getProductDetail(bestProductId);
		BestProductResource bestProductResource = new BestProductResource();
		bestProductResource.setProductId(product.getProductId());
		bestProductResource.setProductName(product.getProductName());
		bestProductResource.setQuantity(map.get(bestProductId));
		return bestProductResource;
	}

	public List<OrderListResource> getTopTenLastestOrder() {
		List<Order> orderList = orderService.getAllOrder();
		orderList.sort((order1, order2) -> order1.getCreatedAt().before(order2.getCreatedAt()) ? 1 : -1);
		return orderList.subList(0, 10 > orderList.size() ? orderList.size() : 10).stream()
				.map(e -> orderHelper.createOrderListResource(e)).collect(Collectors.toList());
	}

	public List<ProductDetailResource> getTopTenLastestProduct() {
		List<Product> productList = productService.getProductList();
		productList.sort((product1, product2) -> product1.getCreatedAt().before(product2.getCreatedAt()) ? 1 : -1);
		return productList.subList(0, 6 > productList.size() ? productList.size() : 6).stream()
				.map(e -> productHelper.createProductDetailResource(e)).collect(Collectors.toList());
	}

	public Integer getHighestPriceOrderId(List<Order> orderList) {
		orderList.sort((order1, order2) -> order1.getNetPrice() > order2.getNetPrice() ? -1 : 1);
		if (orderList.isEmpty())
			return 0;
		return orderList.get(0).getOrderId();
	}
}
