package coffeeshop.resource.order;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import coffeeshop.model.order.OrderStatus;

public class OrderDetailResource {
	private Integer orderId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private String note;
	private Integer netPrice;
	private OrderStatus status;
	private Date createdAt;
	private Date updatedAt;

	private List<OrderProductDetailResource> orderProductList = new LinkedList<OrderProductDetailResource>();

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public List<OrderProductDetailResource> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductDetailResource> orderProductList) {
		this.orderProductList = orderProductList;
	}

}
