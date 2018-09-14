package coffeeshop.model.order;

import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class OrderProduct {
	private Product product;
	private Integer quantity;
	private String productName;
	private ProductType productType;
	private Integer price;
}
