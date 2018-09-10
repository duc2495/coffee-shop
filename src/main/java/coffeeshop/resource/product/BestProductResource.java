package coffeeshop.resource.product;

import lombok.Data;

@Data
public class BestProductResource {
	private String productName;
	private Integer quantity;
	private Integer productId;

	public BestProductResource() {
		this.productId = 0;
		this.quantity = 0;
		this.productName = "";
	}
}
