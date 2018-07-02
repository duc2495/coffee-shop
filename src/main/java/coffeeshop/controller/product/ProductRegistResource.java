package coffeeshop.controller.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import coffeeshop.model.product.ProductType;

public class ProductRegistResource {
	@NotBlank(message = "{error.required}")
	@Size(max = 100)
	private String productName;
	@NotNull
	private ProductType productType;
	@NotNull(message = "{error.required}")
	@Min(0)
	@Max(100000)
	private Integer price;
	@NotBlank
	private String imageUrl;
	private String description;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPrice() {
		return price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
