package coffeeshop.controller.order;

import coffeeshop.controller.product.ProductResource;

public class OrderProductDetailResource extends ProductResource {

	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
