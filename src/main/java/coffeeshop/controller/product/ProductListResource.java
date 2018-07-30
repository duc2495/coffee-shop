package coffeeshop.controller.product;

import java.util.LinkedList;
import java.util.List;

public class ProductListResource<T> {
	private List<T> productList = new LinkedList<T>();

	public List<T> getProductList() {
		return productList;
	}

	public void setProductList(List<T> productList) {
		this.productList = productList;
	}

}
