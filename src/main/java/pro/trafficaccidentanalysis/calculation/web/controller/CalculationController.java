package pro.trafficaccidentanalysis.calculation.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private StoppingDistance stoppingDistance;
	private MessageSource messageSource;
		
	@Autowired
	public CalculationController(CalculationValidation calculationValidation, MessageSource messageSource) {
		this.calculationValidation = calculationValidation;
		this.messageSource = messageSource;
	}

	@PostMapping("/stoppingDistance")
	public String getStoppingDistance(@ModelAttribute("calculation") StoppingDistance stoppingDistance, BindingResult bindingResult, Model model) {
		calculationValidation.validate(stoppingDistance, bindingResult);
		if (bindingResult.hasErrors()) {
			return "index";
		}
		stoppingDistance.calculateStoppingDistance();
		this.stoppingDistance = new StoppingDistance();
		this.stoppingDistance.setSpeed(stoppingDistance.getSpeed());
		this.stoppingDistance.setDeceleration(stoppingDistance.getDeceleration());
		this.stoppingDistance.setStoppingDistance(stoppingDistance.getStoppingDistance());
		return "index";
	}
	
	@GetMapping("/exportToWord")
	public void exportToWord(HttpServletResponse response) throws Exception {
		MessageSourceAccessor accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
		ExportToWord.createWord(accessor.getMessage("documentTitle"), accessor.getMessage("stoppingDistanceCalculation"), accessor.getMessage("stoppingDistance"), this.stoppingDistance, response);
	}	
	
}
