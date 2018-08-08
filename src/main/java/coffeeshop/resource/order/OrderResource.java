package coffeeshop.resource.order;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class OrderResource {

	@NotEmpty(message = "{error.required}")
	private List<OrderProductResource> orderProductList = new LinkedList<OrderProductResource>();
	public List<OrderProductResource> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProductResource> orderProductList) {
		this.orderProductList = orderProductList;
	}
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
}
