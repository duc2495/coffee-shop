package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import coffeeshop.model.order.Order;
import coffeeshop.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

	private static final String viewPrefix = "admin/orders/";

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderHelper orderHelper;

	@GetMapping
	public String getListOrder(Model model) {

		List<Order> orderList = orderService.getAllOrder();

		// resourceに変換
		List<OrderListResource> canceledList = new LinkedList<OrderListResource>();
		List<OrderListResource> orderedList = new LinkedList<OrderListResource>();
		List<OrderListResource> shippingList = new LinkedList<OrderListResource>();
		List<OrderListResource> finishedList = new LinkedList<OrderListResource>();
		List<OrderListResource> orderResourceList = new LinkedList<OrderListResource>();
		for (Order order : orderList) {
			OrderListResource orderResource = orderHelper.createOrderListResource(order);
			orderResourceList.add(orderResource);
			switch (orderResource.getStatus().getValue()) {
			case 0:
				canceledList.add(orderResource);
				break;
			case 1:
				orderedList.add(orderResource);
				break;
			case 2:
				shippingList.add(orderResource);
				break;
			case 3:
				finishedList.add(orderResource);
			}
		}
		model.addAttribute("orderResourceList", orderResourceList);
		model.addAttribute("canceledList", canceledList);
		model.addAttribute("orderedList", orderedList);
		model.addAttribute("shippingList", shippingList);
		model.addAttribute("finishedList", finishedList);
		// return list order view
		return viewPrefix + "orders";
	}

	@GetMapping("/{orderId}")
	public String getOrder(@PathVariable("orderId") Integer orderId, Model model) {
		Order order = orderService.findOrderById(orderId);
		if (order == null) {
			// return 404 view
			return "error";
		}

		// resourceに変換
		OrderDetailResource resource = orderHelper.createOrderDetailResource(order);
		
		model.addAttribute("order", resource);

		// return order detail view
		return viewPrefix + "order";
	}
}
