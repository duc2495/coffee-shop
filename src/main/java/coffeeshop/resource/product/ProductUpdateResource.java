package coffeeshop.resource.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coffeeshop.model.product.ProductType;

public class ProductUpdateResource {
	private int productId;
	@NotBlank(message = "{error.required}")
	@Size(max = 100, message = "{error.maxLength}")
	private String productName;
	@NotNull(message = "{error.required}")
	private ProductType productType;
	@NotNull(message = "{error.required}")
	@Min(value = 0, message = "{error.min}")
	@Max(value = 100000, message = "{error.max}")
	private Integer price;
	private String description;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
