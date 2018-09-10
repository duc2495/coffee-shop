package coffeeshop.model.order;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private Integer orderId;
	private String customerName;
	private String customerAddress;
	private String customerPhone;
	private String note;
	private Integer netPrice;
	private OrderStatus status;
	private Date createdAt;
	private Date updatedAt;
	private List<OrderProduct> orderProductList;
}
