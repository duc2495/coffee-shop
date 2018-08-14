package coffeeshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
	
	@GetMapping(path= {"","/dashboard"})
	public String getDashboard(Model model) {
		return "admin/dashboard";
	}
}
