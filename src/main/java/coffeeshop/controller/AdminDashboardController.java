package coffeeshop.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import coffeeshop.resource.dashboard.DashboardResource;
import coffeeshop.resource.dashboard.IncomeChart;
import coffeeshop.resource.dashboard.ProductChart;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	private Gson gson;

	@GetMapping(path = { "", "/dashboard" })
	public String getDashboard(Model model) {
		return "admin/dashboard";
	}

	@GetMapping("/resource")
	@ResponseBody
	public ResponseEntity<?> getResource(@RequestParam("from") String from, @RequestParam("to") String to) {

		if (!from.equals("")) {
			System.out.println(from + to);
		}
		else {
			System.out.println("none");
		}
		DashboardResource resource = new DashboardResource();
		ArrayList<IncomeChart> incomeList = new ArrayList<IncomeChart>();
		ArrayList<ProductChart> productList = new ArrayList<ProductChart>();
		for (int i = 1; i < 10; i++) {
			incomeList.add(new IncomeChart("2017-0" + i, 100));
		}
		incomeList.add(new IncomeChart("2017-10", 100));
		incomeList.add(new IncomeChart("2017-11", 100));
		incomeList.add(new IncomeChart("2017-12", 100));

		for (int i = 1; i < 10; i++) {
			productList.add(new ProductChart("2017-0" + i, 1, 1, 1));
		}
		productList.add(new ProductChart("2017-10", 1, 1, 1));
		productList.add(new ProductChart("2017-11", 1, 1, 1));
		productList.add(new ProductChart("2017-12", 1, 1, 1));

		resource.setOrderedNumber(10);
		resource.setShippingNumber(20);
		resource.setFinishedNumber(10);
		resource.setCanceledNumber(5);

		resource.setPureCoffee(5);
		resource.setFromCoffee(10);
		resource.setNoneCoffee(4);

		resource.setListIncome(incomeList);
		resource.setListProduct(productList);

		String jsonString = gson.toJson(resource);
		try {
			JsonObject response = new JsonParser().parse(jsonString).getAsJsonObject();
			return new ResponseEntity<JsonObject>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (JsonIOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Resource not exist!", HttpStatus.BAD_REQUEST);

	}

}
