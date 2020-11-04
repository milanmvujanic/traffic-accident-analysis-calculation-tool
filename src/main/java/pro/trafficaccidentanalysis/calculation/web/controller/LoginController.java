package pro.trafficaccidentanalysis.calculation.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistanceData;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String showIndexPage(Model model) {
		model.addAttribute("calculation", new StoppingDistanceData());
		return "index";
	}
}
