package coffeeshop.resource.order;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderRequestResource {
	@NotBlank
	@Size(max = 50)
	private String customerName;

	@NotBlank
	@Size(max = 100)
	private String customerAddress;

	@NotBlank
	@Pattern(regexp = "^$|\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
	@Size(max = 15)
	private String customerPhone;

	@Size(max = 500)
	private String note;

	@NotEmpty
	private List<@Valid OrderProductDetailResource> orderProductList = new LinkedList<OrderProductDetailResource>();
}
