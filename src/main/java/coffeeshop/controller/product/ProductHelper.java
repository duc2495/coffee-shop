package coffeeshop.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.controller.order.OrderProductDetailResource;
import coffeeshop.controller.order.OrderProductResource;
import coffeeshop.model.product.Product;

@Component
public class ProductHelper {
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 商品のresource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public Product createProductModel(ProductRegistResource resource) {
		// 商品情報をマッピング
		Product model = modelMapper.map(resource, Product.class);
		return model;
	}
	
	/**
	 * 商品のupdateResource→model変換
	 * 
	 * @param resource
	 * @return
	 */
	public Product createProductModel(ProductUpdateResource resource) {
		// 商品情報をマッピング
		Product model = modelMapper.map(resource, Product.class);
		return model;
	}

	/**
	 * 商品詳細のmodel→detailResource変換
	 * 
	 * @param models
	 * @return
	 */
	public ProductDetailResource createProductDetailResource(Product model) {
		// 商品情報をマッピング
		ProductDetailResource resource = modelMapper.map(model, ProductDetailResource.class);
		return resource;
	}

	/**
	 * 商品詳細のmodel→resource変換
	 * 
	 * @param models
	 * @return
	 */
	public ProductResource createProductResource(Product model) {
		// 商品情報をマッピング
		ProductResource resource = modelMapper.map(model, ProductResource.class);
		return resource;
	}

	/**
	 * 商品詳細のmodel→updateResource変換
	 * 
	 * @param models
	 * @return
	 */
	public ProductUpdateResource createProductUpdateResource(Product model) {
		// 商品情報をマッピング
		ProductUpdateResource resource = modelMapper.map(model, ProductUpdateResource.class);
		return resource;
	}
	
	public Product createProductModel(OrderProductResource resource){
		return modelMapper.map(resource, Product.class);
	}
	
	public OrderProductDetailResource createOrderProductDetailResource(Product model){
		return modelMapper.map(model, OrderProductDetailResource.class);
	}
}
