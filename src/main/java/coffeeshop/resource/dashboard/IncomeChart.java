package coffeeshop.resource.dashboard;

public class IncomeChart {
	private String date;
	private int value;

	public IncomeChart() {

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
