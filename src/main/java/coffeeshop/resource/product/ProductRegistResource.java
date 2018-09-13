package coffeeshop.resource.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import coffeeshop.model.product.ProductType;
import lombok.Data;

@Data
public class ProductRegistResource {
	@NotBlank
	@Size(max = 100)
	private String productName;
	
	@NotNull
	private ProductType productType;

	@NotNull
	@Min(0)
	@Max(100000)
	private Integer price;

	@NotNull
	private MultipartFile image;

	@Size(max = 1000)
	private String description;
}
