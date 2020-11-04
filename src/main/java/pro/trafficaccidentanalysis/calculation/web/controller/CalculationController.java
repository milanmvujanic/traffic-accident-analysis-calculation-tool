package pro.trafficaccidentanalysis.calculation.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistanceData;
import pro.trafficaccidentanalysis.calculation.web.export.ExportToWord;

@Controller
@RequestMapping("/calculate")
public class CalculationController {

	@PostMapping("/stoppingDistance")
	public String getStoppingDistance(@ModelAttribute("calculation") StoppingDistanceData stoppingDistanceData) {
		stoppingDistanceData.calculateStoppingDistance();
		return "index";
	}
	
	@GetMapping("/exportToWord")
	public void exportToWord(@ModelAttribute("result") String result, HttpServletResponse response) throws Exception {
		ExportToWord.createWord("Stopping_Distance", "Stopping distance calculation", Double.parseDouble(result), response);
	}	
	
}
