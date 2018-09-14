package coffeeshop.resource.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class IncomeChartResource {
	private String date;
	private int value;

	public IncomeChartResource() {

	}

	public IncomeChartResource(Date date, int value) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		this.date = df.format(date);
		this.value = value;
	}

	public IncomeChartResource(String date, int value) {
		this.date = date;
		this.value = value;
	}
}
