package coffeeshop.model.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
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
}
