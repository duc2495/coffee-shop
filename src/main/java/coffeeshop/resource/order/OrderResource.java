package coffeeshop.resource.order;

import java.util.Date;

import coffeeshop.model.order.OrderStatus;
import lombok.Data;

@Data
public class OrderResource {
	private Integer orderId;
	private String customerName;
	private String customerPhone;
	private Integer netPrice;
	private OrderStatus status;
	private Date createdAt;
	private Date updatedAt;
}
