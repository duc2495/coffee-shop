package coffeeshop.resource.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class OrderRequestResource {
	@Min(1)
	Integer orderId;
	@NotBlank
	String customerPhone;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

}
