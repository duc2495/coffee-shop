package coffeeshop.model.order;

import coffeeshop.model.product.Product;

public class OrderProduct {
	
	private Product product;
	private Integer quantity;


	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

}
