package coffeeshop.resource.order;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import coffeeshop.model.order.OrderStatus;
import lombok.Data;


@Data
public class OrderDetailResource {
	private Integer orderId;
	private Integer netPrice;

	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private String note;

	private Date createdAt;
	private Date updatedAt;

	private OrderStatus status;

	private List<OrderProductDetailResource> orderProductList = new LinkedList<OrderProductDetailResource>();
}
