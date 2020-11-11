package pro.trafficaccidentanalysis.calculation.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistance;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String showIndexPage(Model model) {
		model.addAttribute("calculation", new StoppingDistance());
		return "index";
	}
}
