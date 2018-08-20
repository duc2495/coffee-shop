package coffeeshop.resource.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncomeChart {
	private String date;
	private int value;

	public IncomeChart() {

	}
	
	public IncomeChart(Date date, int value){
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		this.date = df.format(date);
		this.value = value;
	}

	public IncomeChart(String date, int value) {
		this.date = date;
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
