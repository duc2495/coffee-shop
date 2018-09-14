package coffeeshop.resource.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class ProductUpdateResource {
	private int productId;

	@NotBlank
	@Size(max = 50)
	private String productName;

	@NotNull
	private ProductType productType;

	@NotNull
	@Min(0)
	@Max(100000)
	private Integer price;

	@Size(max = 1000)
	private String description;
}
