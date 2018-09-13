package coffeeshop.resource.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class OrderRequestResource {
	@Min(1)
	Integer orderId;
	@NotBlank
	String customerPhone;
}
