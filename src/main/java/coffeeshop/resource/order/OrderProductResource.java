package coffeeshop.resource.order;

import javax.validation.constraints.Min;

import coffeeshop.resource.product.ProductResource;
import lombok.Data;

@Data
public class OrderProductResource {
	@Min(1)
	private Integer quantity;
	private  ProductResource product;
}
