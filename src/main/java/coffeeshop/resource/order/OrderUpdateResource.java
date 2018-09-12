package coffeeshop.resource.order;

import coffeeshop.model.order.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateResource {
	private Integer orderId;
	private OrderStatus status;
	private String note;
}
