package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.net.SyslogOutputStream;
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

	@GetMapping("/order_detail")
	public String orderDetail(){
		return "";
	}

	@PostMapping("/submit_order")
	public String receiveListOrderProduct(@Valid @ModelAttribute("orderResource") OrderResource orderResource,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) throws ParseException {

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
		model.addAttribute("order", orderHelper.createOrderDetailResource(order));
		return "/big_store/order_detail";
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
}
