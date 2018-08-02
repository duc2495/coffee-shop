package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import coffeeshop.controller.product.ProductHelper;
import coffeeshop.model.order.Order;
import coffeeshop.controller.product.ListResource;
import coffeeshop.service.OrderService;
import coffeeshop.service.product.ProductService;

@Controller
@RequestMapping("/order")
@SessionAttributes("orderResource")
public class OrderController {

	@Autowired
	private ProductHelper productHelper;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderHelper orderHelper;

	@Autowired
	private MessageSource messageSource;

	// Session attribute
	@ModelAttribute("orderResource")
	private OrderResource orderResource() {
		return new OrderResource();
	}

	@GetMapping("/submit_order")
	public String orderFormPage(@ModelAttribute("orderResource") OrderResource orderResource, Model model) {
		List<OrderProductDetailResource> productList = new LinkedList<OrderProductDetailResource>();
		int total_check = 0;
		for (OrderProductResource p : orderResource.getOrderList()) {
			OrderProductDetailResource pd = productHelper.createOrderProductDetailResource(
					productService.getProductDetail(Integer.parseInt(p.getProductId())));
			pd.setQuantity(p.getQuantity());
			total_check += pd.getQuantity() * pd.getPrice();
			productList.add(pd);
		}

		model.addAttribute("orderResource", orderResource);
		model.addAttribute("orderProductDetailList", productList);
		model.addAttribute("total_check", total_check);
		return "big_store/checkout";
	}

	@GetMapping(value = "/find_order")
	public String getFindOder(Model model, OrderRequestResource orderRequestResource) {
		model.addAttribute("orderRequestResource", orderRequestResource);
		return "big_store/find_order";
	}

	@PostMapping("/find_order")
	public String postFindOrder(
			@Valid @ModelAttribute("orderRequestResource") OrderRequestResource orderRequestResource,
			BindingResult result, RedirectAttributes redirectAttribute) {
		redirectAttribute.addFlashAttribute("orderRequestResource", orderRequestResource);
		return "redirect:/order/order_detail";
	}

	@GetMapping("/order_detail")
	public String orderDetail(@ModelAttribute("orderRequestResource") OrderRequestResource orderRequestResource, Model model) {
		if (orderRequestResource.getOrderId() == null) {
			// return 404 view
			return "big_store/find_order";
		}
		Order order = orderService.findOrderById(orderRequestResource.getOrderId());
		
		if (order == null) {
			// return 404 view
			return "error";
		}
		else if(!order.getCustomerPhone().equals(orderRequestResource.getCustomerPhone())){
			model.addAttribute("info", "結果がありません");
			return "big_store/find_order";
		}

		// resourceに変換
		OrderDetailResource resource = orderHelper.createOrderDetailResource(order);
		model.addAttribute("order", resource);

		// return product detail view
		return "big_store/order";
	}

	@PostMapping("/submit_order")
	public String receiveListOrderProduct(@Valid @ModelAttribute("orderResource") OrderResource orderResource,
			BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale)
			throws ParseException {

		List<OrderProductDetailResource> productList = new LinkedList<OrderProductDetailResource>();
		int total_check = 0;
		for (OrderProductResource p : orderResource.getOrderList()) {
			OrderProductDetailResource pd = productHelper.createOrderProductDetailResource(
					productService.getProductDetail(Integer.parseInt(p.getProductId())));
			pd.setQuantity(p.getQuantity());
			total_check += pd.getQuantity() * pd.getPrice();
			productList.add(pd);
		}
		model.addAttribute("orderResource", orderResource);
		model.addAttribute("orderProductDetailList", productList);
		model.addAttribute("total_check", total_check);
		if (result.hasErrors()) {
			return "big_store/checkout";
		}
		// add order to db
		int id = orderService.insertOrder(orderResource);
		Order order = orderService.findOrderById(id);
		OrderRequestResource orr = new OrderRequestResource();
		orr.setCustomerPhone(order.getCustomerPhone());
		orr.setOrderId(order.getOrderId());
		redirectAttributes.addFlashAttribute("orderRequestResource", orr);
		redirectAttributes.addFlashAttribute("info",
				messageSource.getMessage("info.order.success", new Object[] { order.getOrderId() }, locale));
		return "redirect:/order/order_detail";
	}

	@PostMapping("")
	public String receiveOrder(@ModelAttribute("orderResource") OrderResource orderResource,
			@Valid @RequestBody ListResource<@Valid OrderProductResource> data, BindingResult result, Model model)
			throws ParseException {
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e));
		}
		List<OrderProductDetailResource> productList = new LinkedList<OrderProductDetailResource>();
		int total_check = 0;
		for (OrderProductResource p : data.getProductList()) {
			OrderProductDetailResource pd = productHelper.createOrderProductDetailResource(
					productService.getProductDetail(Integer.parseInt(p.getProductId())));
			pd.setQuantity(p.getQuantity());
			total_check += pd.getQuantity() * pd.getPrice();
			productList.add(pd);
		}

		orderResource.setOrderList(data.getProductList());
		model.addAttribute("orderResource", orderResource);
		model.addAttribute("orderProductDetailList", productList);
		model.addAttribute("total_check", total_check);
		return "big_store/checkout";
	}

	@PatchMapping("/{orderId}")
	public String cancelOrder(@PathVariable("orderId") Integer orderId, Model model) {
		Order order = orderService.findOrderById(orderId);
		if (order == null) {
			// return 404 view
			return "error";
		}
		// Order modelを作成

		// DBにを更新

		// return view
		return "";
	}
}
