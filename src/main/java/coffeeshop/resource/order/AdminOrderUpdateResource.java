package coffeeshop.resource.order;

import coffeeshop.model.order.OrderStatus;

public class AdminOrderUpdateResource {
	private Integer orderId;
	private OrderStatus status;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
