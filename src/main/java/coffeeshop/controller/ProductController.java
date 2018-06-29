package coffeeshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import coffeeshop.model.Product;
import coffeeshop.service.impl.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//method for adding product
	public void addProduct(Product product){
		this.productService.insert(product);
	}
	
	//method for update product
	public void updateProduct(Product product) {
		this.productService.update(product);
	}
	
	//method for delete product
	public void deleteProduct(Integer id) {
		this.productService.delete(id);
	}
	
	//method for find product
	public void findByPrimaryKey(Integer id) {
		this.productService.selectByKey(id);
	}
	
	
}
