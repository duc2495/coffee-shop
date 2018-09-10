package coffeeshop.model.product;

import java.util.Date;

import lombok.Data;

@Data
public class Product {
	private int productId;
	private String productName;
	private ProductType productType;
	private int price;
	private String imageUrl;
	private String description;
	private Date createdAt;
}
