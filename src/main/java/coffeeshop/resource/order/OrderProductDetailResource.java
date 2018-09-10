package coffeeshop.resource.order;

import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class OrderProductDetailResource {
	private Integer productId;
	private Integer quantity;
	private String productName;
	private ProductType productType;
	private Integer price;
}
