package coffeeshop.resource.order;

import coffeeshop.resource.product.ProductDetailResource;

public class OrderProductDetailResource {
	private ProductDetailResource product;
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ProductDetailResource getProduct() {
		return product;
	}
	public void setProduct(ProductDetailResource product) {
		this.product = product;
	}
	
	
}
