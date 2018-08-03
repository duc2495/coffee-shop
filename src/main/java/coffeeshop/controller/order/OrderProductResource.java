package coffeeshop.controller.order;

import javax.validation.constraints.Min;

public class OrderProductResource {
	private String orderId;
	private String productId;

	@Min(value = 1)
	private Integer quantity;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
