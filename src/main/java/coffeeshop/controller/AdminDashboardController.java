package coffeeshop.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import coffeeshop.helper.DashboardHelper;
import coffeeshop.model.dashboard.Dashboard;
import coffeeshop.resource.dashboard.DashboardResource;
import coffeeshop.resource.product.BestProductResource;
import coffeeshop.service.DashboardService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

	@Autowired
	private Gson gson;

	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private DashboardHelper dashboardHelper;

	@GetMapping(path = { "", "/dashboard" })
	public String getDashboard(Model model) {
		model.addAttribute("latestOrders", dashboardService.getNewOrders());
		model.addAttribute("latestProducts", dashboardService.getNewProducts());
		return "admin/dashboard/dashboard";
	}
	
	@GetMapping("/resource")
	@ResponseBody
	public ResponseEntity<?> getResource(@RequestParam("from") String from, @RequestParam("to") String to) {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date dayFrom;
			Date dayTo;
			if (!from.equals("")) {
				dayFrom = df.parse(from);
			} else {
				dayFrom = df.parse(df.format(new Date()));
			}
			if (!to.equals("")) {
				dayTo = df.parse(to);
			} else {
				dayTo = df.parse(df.format(new Date()));
			}
			
			Dashboard dashboard = dashboardService.getDashboard(dayFrom, dayTo);

			DashboardResource resource = dashboardHelper.createDashboardResource(dashboard);
			if (resource.getBestProduct() == null) {
				resource.setBestProduct(new BestProductResource());
			}

			String jsonString = gson.toJson(resource);
			try {
				JsonObject response = new JsonParser().parse(jsonString).getAsJsonObject();
				return new ResponseEntity<JsonObject>(response, new HttpHeaders(), HttpStatus.OK);
			} catch (JsonIOException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("Resource not exist!", HttpStatus.BAD_REQUEST);

		} catch (ParseException e1) {
			e1.printStackTrace();
			return new ResponseEntity<>("Resource not exist!", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/latestOrder")
	public String getLatestOrders(Model model) {
		model.addAttribute("latestOrders", dashboardService.getNewOrders());
		return "admin/dashboard/latestOrders::latestOrders";
	}
}
