package coffeeshop.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import coffeeshop.config.MyLogger;
import coffeeshop.helper.ProductHelper;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.resource.product.ProductDetailResource;
import coffeeshop.service.product.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductHelper productHelper;
	
	private final Integer numPerPage=8;
	
	private static String UPLOAD_DIR = System.getProperty("user.home") + "/coffee-shop/images/products/";
	protected final MyLogger logger = MyLogger.getLogger(getClass());
	
	@GetMapping("/search")
	public String search(@RequestParam("keyWord") String keyWord, Model model){
		List<ProductDetailResource> list = productService.searchByKeyWord(keyWord)
				.stream()
				.map(new Function<Product,ProductDetailResource>() {

					@Override
					public ProductDetailResource apply(Product t) {
						// TODO Auto-generated method stub
						return productHelper.createProductDetailResource(t);
					}
		}).collect(Collectors.toList());
		model.addAttribute("productList", list);
		return "big_store/product_search_page::productList";
	}
	
	@GetMapping("/productpage/{productType}/{page}")
	public String getProductPage(@PathVariable("productType") String productType, @PathVariable("page") Integer page, Model model){
		ProductType type;
		if(productType.equals("purecoffee")) type = ProductType.PURE_COFFEE;
		else if(productType.equals("fromcoffee")) type = ProductType.FROM_COFFEE;
		else type = ProductType.NON_COFFEE;
		int totalListSize = productService.getProductList(type).size();
		int maxPage = totalListSize%numPerPage==0 ? totalListSize/numPerPage : totalListSize/numPerPage + 1;
		List<ProductDetailResource> list = productService.getProductPage(page, numPerPage, type)
				.stream()
				.map(new Function<Product,ProductDetailResource>() {

					@Override
					public ProductDetailResource apply(Product t) {
						// TODO Auto-generated method stub
						return productHelper.createProductDetailResource(t);
					}
		}).collect(Collectors.toList());
		model.addAttribute("productList", list);
		model.addAttribute("activePage", page);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("productType", productType);
		return "big_store/product_type_page::productList";
	}
	
    @GetMapping(value = { "/", "/home" })
    public String home(Model model) {
		List<Product> productList = productService.getProductList();

		// resourceに変換
		List<ProductDetailResource> pureCoffeeList = new LinkedList<ProductDetailResource>();
		List<ProductDetailResource> fromCoffeeList = new LinkedList<ProductDetailResource>();
		List<ProductDetailResource> nonCoffeeList = new LinkedList<ProductDetailResource>();

		for (Product p : productList) {
			ProductDetailResource product = productHelper.createProductDetailResource(p);
			if (product.getProductType().equals(ProductType.FROM_COFFEE))
				fromCoffeeList.add(product);
			else if (p.getProductType().equals(ProductType.NON_COFFEE))
				nonCoffeeList.add(product);
			else
				pureCoffeeList.add(product);
		}

		model.addAttribute("fromCoffeeList", fromCoffeeList);
		model.addAttribute("nonCoffeeList", nonCoffeeList);
		model.addAttribute("pureCoffeeList", pureCoffeeList);
		// return list product view
		return "big_store/index";
    }

    @GetMapping("/product/images")
	public void getImages(@RequestParam("image") String image, HttpServletResponse response) {
		try {
			FileSystemResource file = new FileSystemResource(UPLOAD_DIR + image);
			response.setContentType("image/gif");
			IOUtils.copy(file.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("Error read file: {}", image);
		}
	}
    @RequestMapping(value = { "/403", }, method = RequestMethod.GET)
    public String error403(Model model) {
    	model.addAttribute("error", "You are not authorized to access this page.");
        return "403";
    }
}
