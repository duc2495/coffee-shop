package coffeeshop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import coffeeshop.helper.ProductHelper;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.resource.product.ProductDetailResource;
import coffeeshop.resource.product.ProductRegistResource;
import coffeeshop.resource.product.ProductResource;
import coffeeshop.resource.product.ProductUpdateResource;
import coffeeshop.service.product.ProductService;

@Controller
@RequestMapping("admin/products")
public class AdminProductController extends BaseController {

	private static String UPLOAD_DIR = System.getProperty("user.home") + "/coffee-shop/images/products/";
	private static final String viewPrefix = "admin/products/";
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductHelper productHelper;

	@GetMapping
	public String getProductList(Model model) {
		List<Product> productList = productService.getProductList();

		// resourceに変換
		List<ProductResource> productResources = new LinkedList<ProductResource>();
		List<ProductResource> pureCoffeeList = new LinkedList<ProductResource>();
		List<ProductResource> fromCoffeeList = new LinkedList<ProductResource>();
		List<ProductResource> nonCoffeeList = new LinkedList<ProductResource>();

		for (Product p : productList) {
			ProductResource product = productHelper.createProductResource(p);
			productResources.add(product);
			if (product.getProductType().equals(ProductType.FROM_COFFEE))
				fromCoffeeList.add(product);
			else if (p.getProductType().equals(ProductType.NON_COFFEE))
				nonCoffeeList.add(product);
			else
				pureCoffeeList.add(product);
		}
		model.addAttribute("productResources", productResources);
		model.addAttribute("fromCoffeeList", fromCoffeeList);
		model.addAttribute("nonCoffeeList", nonCoffeeList);
		model.addAttribute("pureCoffeeList", pureCoffeeList);
		// return list product view
		return viewPrefix + "products";
	}

	@GetMapping("/{productId}")
	public String getProduct(@PathVariable("productId") Integer productId, Model model) {
		Product product = productService.getProductDetail(productId);
		if (product == null) {
			// return 404 view
			return "error";
		}

		// resourceに変換
		ProductDetailResource resource = productHelper.createProductDetailResource(product);
		model.addAttribute("product", resource);

		// return product detail view
		return viewPrefix + "product";
	}

	/**
	 * 
	 * @param image
	 * @param response
	 */
	@GetMapping("/images")
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

	@GetMapping("/new")
	public String createProductForm(Model model) {
		ProductRegistResource product = new ProductRegistResource();
		model.addAttribute("product", product);
		return viewPrefix + "create_product";
	}

	/**
	 * 
	 * @param resource
	 * @param model
	 */
	@PostMapping
	public String registProduct(@Valid @ModelAttribute("product") ProductRegistResource resource, BindingResult result,
			Model model, RedirectAttributes redirectAttributes, Locale locale) {
		/* service/repo layer can not access request data of web layer */

		// Validate
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "Error ...");
			return viewPrefix + "create_product";
		}
		String imageUrl;

		try {
			imageUrl = this.doUploadFile(resource.getImage());
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Error ...");
			return viewPrefix + "create_product";
		}

		// 商品modelを作成
		Product product = productHelper.createProductModel(resource);
		product.setImageUrl(imageUrl);

		// DBにインサート
		productService.registProduct(product);

		// return view
		redirectAttributes.addFlashAttribute("info", messageSource.getMessage("info.product.added", null, locale));
		return "redirect:/" + viewPrefix;
	}

	/**
	 * 
	 * @param productId
	 * @param model
	 */
	@GetMapping("/{productId}/update")
	public String updateProductForm(@PathVariable("productId") Integer productId, Model model) {
		Product product = productService.getProductDetail(productId);
		if (product == null) {
			// return 404 view
			return "error";
		}

		// resourceに変換
		ProductUpdateResource resource = productHelper.createProductUpdateResource(product);

		model.addAttribute("product", resource);

		// return product update form
		return viewPrefix + "update_product";
	}

	/**
	 * 
	 * @param productId
	 * @param resource
	 * @param model
	 */
	@PatchMapping("/{productId}")
	public String updateProduct(@PathVariable("productId") Integer productId,
			@Valid @ModelAttribute("product") ProductUpdateResource resource, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {

		// Validate
		if (result.hasErrors()) {
			return viewPrefix + "update_product";
		}

		// 商品のアクセス権限をチェック
		if (!productService.existProduct(productId)) {
			// return forbidden view
			return "403";
		}
		resource.setProductId(productId);

		// 商品modelを作成
		Product product = productHelper.createProductModel(resource);

		// DBにを更新
		productService.updateProductInfo(product);

		// return view
		redirectAttributes.addAttribute("productId", productId);
		redirectAttributes.addFlashAttribute("info", "Product updated successfully");
		return "redirect:/" + viewPrefix + "{productId}";
	}

	@DeleteMapping("/{productId}")
	public String deleteProduct(@PathVariable("productId") Integer productId, Model model,
			RedirectAttributes redirectAttributes) {

		// 商品のアクセス権限をチェック
		if (!productService.existProduct(productId)) {
			// return forbidden view
			return "403";
		}

		// 商品削除
		productService.deleteProduct(productId);

		// return view
		redirectAttributes.addFlashAttribute("info", "Product deleted successfully");
		return "redirect:/" + viewPrefix;
	}

	@PatchMapping("/{productId}/updateImage")
	@ResponseBody
	public ResponseEntity<?> uploadFile(@PathVariable("productId") Integer productId,
			@RequestParam("image") MultipartFile image) {

		if (image.isEmpty()) {
			return new ResponseEntity<>("Please select a image!", HttpStatus.BAD_REQUEST);
		}

		// 商品のアクセス権限をチェック
		if (!productService.existProduct(productId)) {
			// return forbidden view
			return new ResponseEntity<>("Product not exist!", HttpStatus.BAD_REQUEST);
		}
		try {
			String imageUrl = this.doUploadFile(image);

			// 商品modelを作成
			Product product = new Product();
			product.setProductId(productId);
			product.setImageUrl(imageUrl);

			// DBにを更新
			productService.updateProductImage(product);
		} catch (IOException e) {
			return new ResponseEntity<>("Error update image!", HttpStatus.BAD_REQUEST);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("imageUrl", image.getOriginalFilename());
		resultMap.put("message", "Product image updated successfully");
		return new ResponseEntity<HashMap<String, Object>>(resultMap, new HttpHeaders(), HttpStatus.OK);
	}

	private String doUploadFile(MultipartFile multipartFile) throws IOException {

		// Tạo thư mục gốc upload nếu chưa tồn tại.
		File uploadRootDir = new File(UPLOAD_DIR);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		if (multipartFile == null) {
			return null;
		}
		// Tên file gốc tại Client.
		String name = multipartFile.getOriginalFilename();
		if (name.length() > 0) {
			try {
				// Tạo file tại Server.
				File serverFile = new File(UPLOAD_DIR + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(multipartFile.getBytes());
				stream.close();
				//
				logger.info("Write file: {}", serverFile);
				return name;
			} catch (IOException e) {
				logger.error("Error write file: {}", name);
				throw e;
			}
		}
		return null;
	}
}
