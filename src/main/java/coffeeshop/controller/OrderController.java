package coffeeshop.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import coffeeshop.helper.OrderHelper;
import coffeeshop.helper.ProductHelper;
import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderProduct;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.resource.ListResource;
import coffeeshop.resource.order.OrderUpdateResource;
import coffeeshop.resource.order.OrderDetailResource;
import coffeeshop.resource.order.OrderProductDetailResource;
import coffeeshop.resource.order.OrderProductResource;
import coffeeshop.resource.order.OrderProductRequestResource;
import coffeeshop.resource.order.OrderRequestResource;
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
	/**
	 * セッションスコープのビーンを作る 方法
	 * 
	 * @return セッションスコープのビーンのモデルアトリビュート
	 */
	@ModelAttribute("orderResource")
	private OrderRequestResource orderResource() {
		return new OrderRequestResource();
	}

	/**
	 * クライアントからお客の情報がない発注を受ける方法
	 * 
	 * @param orderResource
	 * @param model
	 * @return お客情報フォームのビュー
	 */
	@GetMapping("/submit_order")
	public String orderFormPage(@ModelAttribute("orderResource") OrderRequestResource orderResource, Model model) {
		int total_check = 0;
		for (OrderProductDetailResource pd : orderResource.getOrderProductList()) {
			total_check += pd.getQuantity() * pd.getPrice();
		}
		model.addAttribute("orderResource", orderResource);
		model.addAttribute("orderProductDetailList", orderResource.getOrderProductList());
		model.addAttribute("total_check", total_check);
		return "big_store/checkout";
	}

	/**
	 * オーダーを探すフォームページを取る方法
	 * 
	 * @param model
	 * @param orderRequestResource
	 * @return オーダーを探すフォームページ
	 */
	@GetMapping(value = "/find_order")
	public String getFindOder(Model model, OrderProductRequestResource orderRequestResource) {
		model.addAttribute("orderRequestResource", orderRequestResource);
		return "big_store/find_order";
	}

	/**
	 * オーダーIdとお客の電話番号でオーダーを探す方法
	 * 
	 * @param orderRequestResource
	 * @param result
	 * @param redirectAttribute
	 * @return 探した結果を表示するページ
	 */
	@PostMapping("/find_order")
	public String postFindOrder(
			@Valid @ModelAttribute("orderRequestResource") OrderProductRequestResource orderRequestResource,
			BindingResult result, RedirectAttributes redirectAttribute) {
		redirectAttribute.addFlashAttribute("orderRequestResource", orderRequestResource);
		return "redirect:/order/order_detail";
	}

	/**
	 * オーダーの詳しい情報を表示するページを取る方法
	 * 
	 * @param orderRequestResource
	 * @param model
	 * @return オーダーの詳しい情報を表示するページ
	 */
	@GetMapping("/order_detail")
	public String orderDetail(@ModelAttribute("orderRequestResource") OrderProductRequestResource orderRequestResource,
			Model model) {
		if (orderRequestResource.getOrderId() == null) {
			// return 404 view
			return "big_store/find_order";
		}
		Order order = orderService.findOrderById(orderRequestResource.getOrderId());

		if (order == null) {
			model.addAttribute("info", "結果がありません");
			return "big_store/find_order";
		} else if (!order.getCustomerPhone().equals(orderRequestResource.getCustomerPhone())) {
			model.addAttribute("info", "結果がありません");
			return "big_store/find_order";
		}

		// resourceに変換
		OrderDetailResource resource = orderHelper.createOrderDetailResource(order);
		model.addAttribute("order", resource);

		// return product detail view
		return "big_store/order";
	}

	/**
	 * クライアントから完成オーダーリクエストを受ける方法
	 * 
	 * @param orderResource
	 * @param result
	 * @param redirectAttributes
	 * @param model
	 * @param locale
	 * @return もし入力したデータにミスがない場合、オーダー情報を表示うるページを送ります 逆に元の所に戻って、エラーを表示儀ます。
	 * @throws ParseException
	 */
	@PostMapping("/submit_order")
	public String receiveListOrderProduct(@Valid @ModelAttribute("orderResource") OrderRequestResource orderResource,
			BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale)
			throws ParseException {

		List<OrderProduct> orderProductList = new LinkedList<OrderProduct>();
		List<Product> productListModel = new LinkedList<Product>();
		int total_check = 0;
		for (OrderProductDetailResource pd : orderResource.getOrderProductList()) {
			total_check += pd.getQuantity() * pd.getPrice();
			orderProductList.add(orderHelper.createOrderProductModel(pd));
			productListModel.add(productHelper.createProductModel(pd));
		}
		// check input data(OrderProductDetailResource) with database(Product)
		if(productService.countProducts(productListModel) != productListModel.size()) {
			redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.product.not_match", null, locale));
			return "redirect:/";
		}
		
		model.addAttribute("orderResource", orderResource);
		model.addAttribute("total_check", total_check);
		if (result.hasErrors()) {
			return "big_store/checkout";
		}
		
		// 注文modelを作成
		Order order = orderHelper.createOrderModel(orderResource);
		order.setOrderProductList(orderProductList);
		order.setStatus(OrderStatus.ORDERED);
		order.setNetPrice(total_check);
		
		// DBにインサート
		int orderId = orderService.insertOrder(order);
		
		// return order detail view
		OrderProductRequestResource orderRequest = new OrderProductRequestResource();
		orderRequest.setOrderId(orderId);
		orderRequest.setCustomerPhone(order.getCustomerPhone());
		redirectAttributes.addFlashAttribute("orderRequestResource", orderRequest);
		redirectAttributes.addFlashAttribute("info",
				messageSource.getMessage("info.order.success", new Object[] { order.getOrderId() }, locale));
		return "redirect:/order/order_detail";
	}

	@PostMapping("")
	public String receiveOrder(@ModelAttribute("orderResource") OrderRequestResource orderResource,
			@Valid @RequestBody ListResource<coffeeshop.resource.order.OrderProductResource> data, BindingResult result,
			Model model) throws ParseException {
		List<OrderProductDetailResource> productList = new LinkedList<OrderProductDetailResource>();
		int total_check = 0;
		for (OrderProductResource p : data.getProductList()) {
			OrderProductDetailResource pd = productHelper
					.createOrderProductDetailResource(productService.getProductDetail(p.getProduct().getProductId()));
			pd.setQuantity(p.getQuantity());
			total_check += pd.getQuantity() * pd.getPrice();
			productList.add(pd);
		}

		orderResource.setOrderProductList(productList);
		model.addAttribute("orderResource", orderResource);
		model.addAttribute("total_check", total_check);
		return "big_store/checkout";
	}

	@PatchMapping("/update_order")
	public String udpateNote(@ModelAttribute("order") OrderDetailResource orderDetailResource, Model model,
			Locale locale) {
		Order order = orderService.findOrderById(orderDetailResource.getOrderId());
		order.setNote(orderDetailResource.getNote());
		orderService.updateOrder(order);
		orderDetailResource = orderHelper.createOrderDetailResource(order);
		model.addAttribute("info", messageSource.getMessage("info.order.updated", null, locale));
		model.addAttribute("order", orderDetailResource);
		return "big_store/order";
	}

	@PostMapping("/update_order")
	public String getUpdateOrderPage(@ModelAttribute("order") OrderDetailResource orderDetailResource, Model model,
			Locale locale) {
		Order order = orderService.findOrderById(orderDetailResource.getOrderId());
		orderDetailResource = orderHelper.createOrderDetailResource(order);
		model.addAttribute("order", orderDetailResource);
		return "big_store/update_order";
	}

	/**
	 * 
	 * @param orderId
	 * @param resource
	 * @param model
	 */
	@PatchMapping("/update")
	@ResponseBody
	public ResponseEntity<?> updateOrder(@Valid @ModelAttribute("order") OrderUpdateResource resource, Model model,
			Locale locale) {

		if (!orderService.hasOrder(resource.getOrderId())) {
			return new ResponseEntity<>("Order not exist!", HttpStatus.BAD_REQUEST);
		}

		// 注文modelを作成
		Order order = orderService.findOrderById(resource.getOrderId());
		if (resource.getNote().length()>500) {
			return new ResponseEntity<>("メモは500文字未満でなければなりません", HttpStatus.BAD_REQUEST);
		}
		order.setNote(resource.getNote());

		// DBにを更新
		orderService.updateOrder(order);

		// return view
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("note", order.getNote());
		resultMap.put("message", messageSource.getMessage("info.order.updated", null, locale));
		return new ResponseEntity<HashMap<String, String>>(resultMap, new HttpHeaders(), HttpStatus.OK);
	}

	@PatchMapping("/{orderId}")
	public String cancelOrder(@PathVariable("orderId") Integer orderId, Model model, Locale locale) {
		Order order = orderService.findOrderById(orderId);
		if (order == null) {
			// return 404 view
			return "error";
		}
		
		if (!order.getStatus().equals(OrderStatus.ORDERED)) {
			model.addAttribute("error", messageSource.getMessage("error.order.cancel.failded", null, locale));
		}
		else {
			// Order modelを作成
			order.setStatus(OrderStatus.CANCELED);
			// DBにを更新
			orderService.updateOrder(order);
			// return view
			model.addAttribute("info", messageSource.getMessage("info.order.canceled", null, locale));
		}
		OrderDetailResource resource = orderHelper.createOrderDetailResource(order);
		model.addAttribute("order", resource);
		return "big_store/order";
	}
}
