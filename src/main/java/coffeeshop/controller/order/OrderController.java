package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.SyslogOutputStream;
import coffeeshop.controller.product.ProductHelper;
import coffeeshop.controller.product.ProductListResource;
import coffeeshop.service.product.ProductService;

@Controller
public class OrderController {

	@Autowired
	private ProductHelper productHelper;
	@Autowired
	private ProductService productService;

	@PostMapping("/submit_order")
	public String receiveListOrderProduct(@ModelAttribute OrderResource orderResource, Errors errors,
			Model model) throws ParseException {
		// If error, just return a 400 bad request, along with the error message
		ProductListResource<OrderProductDetailResource> productList = new ProductListResource<OrderProductDetailResource>();
		model.asMap().keySet().forEach(e->System.out.println(e));
		return "home";
	}

	@PostMapping("/order")
	public String receiveOrder(@RequestBody ProductListResource<OrderProductResource> data, Errors errors, Model model)
			throws ParseException {
		List<OrderProductDetailResource> productList = new LinkedList<OrderProductDetailResource>();
		int total_check = 0;
		for (OrderProductResource p : data.getProductList()) {
			OrderProductDetailResource pd = productHelper.createOrderProductDetailResource(
					productService.getProductDetail(Integer.parseInt(p.getProductId())));
			pd.setQuantity(Integer.parseInt(p.getQuantity()));
			total_check+=pd.getQuantity()*pd.getPrice();
			productList.add(pd);
		}
		model.addAttribute("orderProductDetailList", productList);
		model.addAttribute("total_check", total_check);
		return "big_store/checkout :: content";
	}
}
