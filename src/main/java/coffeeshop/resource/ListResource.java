package coffeeshop.resource;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

public class ListResource<T> {
	
	private List<@Valid T> productList = new LinkedList<T>();

	public List<T> getProductList() {
		return productList;
	}

	public void setProductList(List<T> productList) {
		this.productList = productList;
	}

}
