package coffeeshop.controller.order;

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

	private List<OrderProductDetailResource> orderProductDetailList = new LinkedList<OrderProductDetailResource>();

	public List<OrderProductDetailResource> getOrderProductDetailList() {
		return orderProductDetailList;
	}

	public void setOrderProductDetailList(List<OrderProductDetailResource> orderProductDetailList) {
		this.orderProductDetailList = orderProductDetailList;
	}

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

}
