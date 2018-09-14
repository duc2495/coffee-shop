package coffeeshop.resource.product;

import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class ProductResource {
	private int productId;
	private String productName;
	private ProductType productType;
	private Integer price;
	private String description;
}
