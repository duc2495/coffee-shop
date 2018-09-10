package coffeeshop.resource.order;

import javax.validation.constraints.Min;

import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class OrderProductDetailResource {
	@Min(1)
	private Integer quantity;
	private Integer productId;
	private String productName;
	private ProductType productType;
	private Integer price;
}
