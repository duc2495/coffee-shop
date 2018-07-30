package coffeeshop.model.order;
import java.util.Map;

public class Order {

	private Map<Integer, Integer> orderProductMap;
	private Integer orderId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private OrderStatus status;

	public Map<Integer, Integer> getOrderProductMap() {
		return orderProductMap;
	}
	public void setOrderProductSet(Map<Integer, Integer> orderProductSet) {
		this.orderProductMap = orderProductSet;
	}
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
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
}
