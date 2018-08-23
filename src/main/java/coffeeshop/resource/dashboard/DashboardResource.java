package coffeeshop.resource.dashboard;

import java.util.ArrayList;
import java.util.List;

import coffeeshop.resource.order.OrderListResource;
import coffeeshop.resource.product.BestProductResource;
import coffeeshop.resource.product.ProductDetailResource;

public class DashboardResource {

	private int newOrderNumber;
	private int newProductNumber;
	private int income;
	private BestProductResource bestProduct;
	private int highestPriceOrderId;
	private int orderedNumber;
	private int shippingNumber;
	private int finishedNumber;
	private int canceledNumber;

	private int totalProducts;
	private int pureCoffee;
	private int fromCoffee;
	private int noneCoffee;

	private List<OrderListResource> latestOrders;
	private List<ProductDetailResource> newProducts;

	private ArrayList<IncomeChart> listIncome;
	private ArrayList<ProductChart> listProduct;

	public int getNewOrderNumber() {
		return newOrderNumber;
	}

	public void setNewOrderNumber(int newOrderNumber) {
		this.newOrderNumber = newOrderNumber;
	}

	public int getNewProductNumber() {
		return newProductNumber;
	}

	public void setNewProductNumber(int newProductNumber) {
		this.newProductNumber = newProductNumber;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public BestProductResource getBestProduct() {
		return bestProduct;
	}

	public void setBestProduct(BestProductResource bestProduct) {
		this.bestProduct = bestProduct;
	}

	public int getOrderedNumber() {
		return orderedNumber;
	}

	public void setOrderedNumber(int orderedNumber) {
		this.orderedNumber = orderedNumber;
	}

	public int getShippingNumber() {
		return shippingNumber;
	}

	public void setShippingNumber(int shippingNumber) {
		this.shippingNumber = shippingNumber;
	}

	public int getFinishedNumber() {
		return finishedNumber;
	}

	public void setFinishedNumber(int finishedNumber) {
		this.finishedNumber = finishedNumber;
	}

	public int getCanceledNumber() {
		return canceledNumber;
	}

	public void setCanceledNumber(int canceledNumber) {
		this.canceledNumber = canceledNumber;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public int getPureCoffee() {
		return pureCoffee;
	}

	public void setPureCoffee(int pureCoffee) {
		this.pureCoffee = pureCoffee;
	}

	public int getFromCoffee() {
		return fromCoffee;
	}

	public void setFromCoffee(int fromCoffee) {
		this.fromCoffee = fromCoffee;
	}

	public int getNoneCoffee() {
		return noneCoffee;
	}

	public void setNoneCoffee(int noneCoffee) {
		this.noneCoffee = noneCoffee;
	}

	public List<OrderListResource> getLatestOrders() {
		return latestOrders;
	}

	public void setLatestOrders(List<OrderListResource> latestOrders) {
		this.latestOrders = latestOrders;
	}

	public List<ProductDetailResource> getNewProducts() {
		return newProducts;
	}

	public void setNewProducts(List<ProductDetailResource> newProducts) {
		this.newProducts = newProducts;
	}

	public ArrayList<IncomeChart> getListIncome() {
		return listIncome;
	}

	public void setListIncome(ArrayList<IncomeChart> listIncome) {
		this.listIncome = listIncome;
	}

	public ArrayList<ProductChart> getListProduct() {
		return listProduct;
	}

	public void setListProduct(ArrayList<ProductChart> listProduct) {
		this.listProduct = listProduct;
	}

	public int getHighestPriceOrderId() {
		return highestPriceOrderId;
	}

	public void setHighestPriceOrderId(int highestPriceOrderId) {
		this.highestPriceOrderId = highestPriceOrderId;
	}

}
