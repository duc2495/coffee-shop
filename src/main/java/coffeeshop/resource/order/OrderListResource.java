package coffeeshop.resource.order;

import java.util.Date;

import coffeeshop.model.order.OrderStatus;

public class OrderListResource {
	private Integer orderId;
	private String customerName;
	private String customerPhone;
	private Integer netPrice;
	private OrderStatus status;
	private Date createdAt;
	private Date updatedAt;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public Integer getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Integer netPrice) {
		this.netPrice = netPrice;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
