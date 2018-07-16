package coffeeshop.controller.product;

import java.util.LinkedList;
import java.util.List;

public class ProductListResource {
	private List<ProductResource> pureCoffeeList = new LinkedList<ProductResource>();
	private List<ProductResource> fromCoffeeList = new LinkedList<ProductResource>();
	private List<ProductResource> nonCoffeeList = new LinkedList<ProductResource>();

	public List<ProductResource> getPureCoffeeList() {
		return pureCoffeeList;
	}

	public void setPureCoffeeList(List<ProductResource> pureCoffeeList) {
		this.pureCoffeeList = pureCoffeeList;
	}

	public List<ProductResource> getFromCoffeeList() {
		return fromCoffeeList;
	}

	public void setFromCoffeeList(List<ProductResource> fromCoffeeList) {
		this.fromCoffeeList = fromCoffeeList;
	}

	public List<ProductResource> getNonCoffeeList() {
		return nonCoffeeList;
	}

	public void setNonCoffeeList(List<ProductResource> nonCoffeeList) {
		this.nonCoffeeList = nonCoffeeList;
	}

}
