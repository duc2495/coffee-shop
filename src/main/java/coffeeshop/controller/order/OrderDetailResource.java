package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;


public class OrderDetailResource {
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
	private Integer orderId;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	private String customerName;

	private String customerAddress;

	private String customerPhone;
	private String note;
}
