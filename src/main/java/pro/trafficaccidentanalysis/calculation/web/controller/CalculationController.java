package pro.trafficaccidentanalysis.calculation.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistance;
import pro.trafficaccidentanalysis.calculation.web.calculation.validation.CalculationValidation;
import pro.trafficaccidentanalysis.calculation.web.export.ExportToWord;

@Controller
@RequestMapping("/calculate")
public class CalculationController {
	
	private CalculationValidation calculationValidation;
	
	@Autowired
	public CalculationController(CalculationValidation calculationValidation) {
		this.calculationValidation = calculationValidation;
	}

	@PostMapping("/stoppingDistance")
	public String getStoppingDistance(@ModelAttribute("calculation") StoppingDistance stoppingDistance, BindingResult bindingResult) {
		calculationValidation.validate(stoppingDistance, bindingResult);
		if (bindingResult.hasErrors()) {
			return "index";
		}
		stoppingDistance.calculateStoppingDistance();
		return "index";
	}
	
	@GetMapping("/exportToWord")
	public void exportToWord(@ModelAttribute("result") String result, HttpServletResponse response) throws Exception {
		ExportToWord.createWord("Stopping_Distance", "Stopping distance calculation", result, response);
	}	
	
}
