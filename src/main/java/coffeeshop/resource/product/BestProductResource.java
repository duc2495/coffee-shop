package coffeeshop.resource.product;

public class BestProductResource {
	private String productName;
	private Integer quantity;
	private Integer productId;

	public BestProductResource() {
		this.productId = 0;
		this.quantity = 0;
		this.productName = "";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
