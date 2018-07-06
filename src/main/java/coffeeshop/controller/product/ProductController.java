package coffeeshop.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import coffeeshop.controller.BaseController;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.service.product.ProductService;

@Controller
@RequestMapping("admin/products")
public class ProductController extends BaseController {

	private static String UPLOAD_DIR = System.getProperty("user.home") + "/coffee-shop/images/products/";
	private static final String viewPrefix = "admin/products/";

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductHelper productHelper;

	@GetMapping
	public String getProductList(Model model) {
		List<Product> productList = productService.getProductList();

		if (productList.isEmpty()) {
			// return 404 view
			return "error";
		}

		// resourceに変換
		List<ProductResource> pureCoffeeList = new LinkedList<ProductResource>();
		List<ProductResource> fromCoffeeList = new LinkedList<ProductResource>();
		List<ProductResource> nonCoffeeList = new LinkedList<ProductResource>();
		ProductListResource resources = new ProductListResource();

		for (Product p : productList) {
			ProductResource product = productHelper.createProductResource(p);
			if (product.getProductType().equals(ProductType.FROM_COFFEE))
				fromCoffeeList.add(product);
			else if (p.getProductType().equals(ProductType.NON_COFFEE))
				nonCoffeeList.add(product);
			else
				pureCoffeeList.add(product);
		}

		resources.setFromCoffeeList(fromCoffeeList);
		resources.setNonCoffeeList(nonCoffeeList);
		resources.setPureCoffeeList(pureCoffeeList);
		model.addAttribute("resources", resources);
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
		model.addAttribute("resource", resource);
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
			FileSystemResource file = new FileSystemResource(image);
			response.setContentType("image/gif");
			IOUtils.copy(file.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("Error read file: {}", image);
		}
	}

	@GetMapping("/new")
	public String createProductFrom(Model model) {
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
			Model model) {
		/* service/repo layer can not access request data of web layer */

		// Validate
		if (result.hasErrors()) {
			return viewPrefix + "create_product";
		}

		String imageUrl = this.doUploadFile(resource);
		if (imageUrl == null) {
			System.out.println("Failed image upload");
			return viewPrefix + "create_product";
		}
		// 商品modelを作成
		Product product = productHelper.createProductModel(resource);
		product.setImageUrl(imageUrl);

		// DBにインサート
		productService.registProduct(product);

		// return view
		return "redirect:/" + viewPrefix;
	}

	/**
	 * <pre>
	 * vì resource của update giống với regist nên anh dùng chung class ProductRegistResource.
	 * trường hợp có khác nhau thì tạo class ProductEditResource riêng
	 * </pre>
	 * 
	 * @param productId
	 * @param resource
	 * @param model
	 */
	@PatchMapping("/{productId}")
	public String updateProduct(@PathVariable("productId") Integer productId,
			@Valid @RequestBody ProductRegistResource resource, Model model) {

		// 商品のアクセス権限をチェック
		if (!productService.existProduct(productId)) {
			// return forbidden view
			return "403";
		}

		// 商品modelを作成
		Product product = productHelper.createProductModel(resource);

		// DBにを更新
		productService.updateProduct(product);

		// return view
		return "redirect:/" + viewPrefix + "/" + productId;
	}

	@DeleteMapping("/{productId}")
	public String deleteProduct(@PathVariable("productId") Integer productId, Model model) {

		// 商品のアクセス権限をチェック
		if (!productService.existProduct(productId)) {
			// return forbidden view
			return "403";
		}

		// 商品削除
		productService.deleteProduct(productId);

		// return view
		return "redirect:/" + viewPrefix;
	}

	private String doUploadFile(ProductRegistResource resource) {

		// Tạo thư mục gốc upload nếu chưa tồn tại.
		File uploadRootDir = new File(UPLOAD_DIR);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile image = resource.getImage();
		if (image == null) {
			return null;
		}
		// Tên file gốc tại Client.
		String name = image.getOriginalFilename();
		if (name != null && name.length() > 0) {
			try {
				// Tạo file tại Server.
				File serverFile = new File(UPLOAD_DIR + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(image.getBytes());
				stream.close();
				//
				logger.debug("Write file: {}", serverFile);
				return name;
			} catch (Exception e) {
				logger.error("Error write file: {}", name);
			}
		}
		return null;
	}
}
