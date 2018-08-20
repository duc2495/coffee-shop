package coffeeshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.ProductType;
import coffeeshop.resource.dashboard.DashboardResource;
import coffeeshop.resource.dashboard.IncomeChart;
import coffeeshop.resource.dashboard.ProductChart;
import coffeeshop.service.DashboardService;
import coffeeshop.service.OrderService;

@Component
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private OrderService orderService;
	@Override
	public DashboardResource getDashboardResource(Date dayFrom, Date dayTo) {
		DashboardResource resource = new DashboardResource();
		List<Order> orderList = orderService.getNewOrderInTimeInterval(dayFrom, dayTo);
		ArrayList<IncomeChart> incomeList = new ArrayList<IncomeChart>();
		ArrayList<ProductChart> productList = new ArrayList<ProductChart>();
		
		for(int i = -11; i<=-1; i++){
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(dayTo);
			Calendar lastDate = Calendar.getInstance();
			lastDate.setTime(dayTo);
			firstDate.add(Calendar.MONTH, i);
			firstDate.set(Calendar.DATE, firstDate.getActualMinimum(Calendar.DATE));
			lastDate.add(Calendar.MONTH, i);
			lastDate.set(Calendar.DATE, lastDate.getActualMaximum(Calendar.DATE));
			incomeList.add(new IncomeChart(firstDate.getTime(), getIncome(orderList, firstDate.getTime(), lastDate.getTime())));
		}
		Calendar firstDate = Calendar.getInstance();
		firstDate.setTime(dayTo);
		firstDate.set(Calendar.DATE, firstDate.getActualMinimum(Calendar.DATE));
		incomeList.add(new IncomeChart(dayTo, getIncome(orderList, firstDate.getTime(), dayTo)));
		


		resource.setOrderedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.ORDERED));
		resource.setShippingNumber(getNumberOfOrderInStatus(orderList, OrderStatus.SHIPPING));
		resource.setFinishedNumber(getNumberOfOrderInStatus(orderList, OrderStatus.FINISHED));
		resource.setCanceledNumber(getNumberOfOrderInStatus(orderList, OrderStatus.CANCELED));

		resource.setPureCoffee(getNumberOfProductInType(orderList, ProductType.PURE_COFFEE));
		resource.setFromCoffee(getNumberOfProductInType(orderList, ProductType.FROM_COFFEE));
		resource.setNoneCoffee(getNumberOfProductInType(orderList, ProductType.NON_COFFEE));

		resource.setListIncome(incomeList);
		resource.setListProduct(productList);
		return resource;
	}
	
	
	private Integer getNumberOfOrderInStatus(List<Order> orderList, OrderStatus status){
		return orderList.stream().filter(o->{
			return o.getStatus().equals(status);
		}).collect(Collectors.toList()).size();
	}
	
	private Integer getNumberOfProductInType(List<Order> orderList, ProductType type){
		return orderList.stream()
				.map(e->e.getOrderProductList()
						.stream()
						.filter(p->p.getProductType().equals(type))
						.collect(Collectors.toList())
						.size())
				.collect(Collectors.summingInt(e->e));
	}
	
	private Integer getIncome(List<Order> orderList, Date dayFrom, Date dayTo){
		return orderList.stream()
				.filter(o->o.getStatus().equals(OrderStatus.FINISHED)&&!o.getCreatedAt().before(dayFrom)&&!o.getCreatedAt().after(dayTo))
				.map(e->e.getOrderProductList()
						.stream()
						.collect(Collectors.summingInt(q->q.getQuantity()*q.getPrice())))
				.collect(Collectors.summingInt(i->i));
	}
}
