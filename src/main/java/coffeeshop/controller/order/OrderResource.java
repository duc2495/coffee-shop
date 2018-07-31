package coffeeshop.controller.order;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class OrderResource {

	private List<OrderProductResource> orderList = new LinkedList<OrderProductResource>();
	@NotBlank(message = "{error.required}")
	private String customerName;
	@NotBlank(message = "{error.required}")
	private String customerAddress;
	@NotBlank(message = "{error.required}")
	@Pattern(regexp="\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message="{error.phone.invalid}")
	private String customerPhone;
	
	private String note;
	
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
	public List<OrderProductResource> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderProductResource> orderList) {
		this.orderList = orderList;
	}
	
}
