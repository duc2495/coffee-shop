package coffeeshop.resource.order;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderResource {

	@NotEmpty(message = "{error.required}")
	private List<@Valid OrderProductResource> orderProductList = new LinkedList<OrderProductResource>();

	@NotBlank(message = "{error.required}")
	@Size(max = 50, message = "{error.maxLength.customerName}")
	private String customerName;

	@NotBlank(message = "{error.required}")
	@Size(max = 100, message = "{error.maxLength.customerAddress}")
	private String customerAddress;

	@NotBlank(message = "{error.required}")
	@Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "{error.phone.invalid}")
	@Size(max = 15, message = "{error.maxLength.customerPhone}")
	private String customerPhone;

	@Size(max = 500, message = "{error.maxLength.note}")
	private String note;

	public List<OrderProductResource> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductResource> orderProductList) {
		this.orderProductList = orderProductList;
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

}
