package coffeeshop.resource.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductChart {
	private String date;
	private int pureCoffee;
	private int fromCoffee;
	private int noneCoffee;

	public ProductChart() {

	}

	public ProductChart(String date, int pureCoffee, int fromCoffee, int noneCoffee) {
		this.date = date;
		this.pureCoffee = pureCoffee;
		this.fromCoffee = fromCoffee;
		this.noneCoffee = noneCoffee;
	}

	public ProductChart(Date date, int pureCoffee, int fromCoffee, int noneCoffee) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		this.date = df.format(date);
		this.pureCoffee = pureCoffee;
		this.fromCoffee = fromCoffee;
		this.noneCoffee = noneCoffee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

}
