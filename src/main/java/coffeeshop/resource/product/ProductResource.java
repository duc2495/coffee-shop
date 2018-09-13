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
	
	public boolean equals(Object o) {
		if(o instanceof ProductResource) {
			boolean result = ((ProductResource) o).getProductName().equals(this.productName)
					&& ((ProductResource) o).getPrice().equals(this.price);
			return result;
		}
		return false;
	}
	
	public int hashCode() {
		return this.productId;
	}
}
