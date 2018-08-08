package coffeeshop.resource.order;

import javax.validation.constraints.Min;

import coffeeshop.resource.product.ProductResource;

public class OrderProductResource {
	
	private  ProductResource product;
	@Min(value=1)
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ProductResource getProduct() {
		return product;
	}
	public void setProduct(ProductResource product) {
		this.product = product;
	}
}
