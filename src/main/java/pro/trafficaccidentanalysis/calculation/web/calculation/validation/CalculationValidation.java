package pro.trafficaccidentanalysis.calculation.web.calculation.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pro.trafficaccidentanalysis.calculation.web.calculation.Calculation;
import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistance;

@Component
public class CalculationValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Calculation.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StoppingDistance stoppingDistance = (StoppingDistance) target;
		double speed = 0.0;		
		double deceleration = 0.0;
		
		try {
			if (stoppingDistance.getSpeed().endsWith("d") || stoppingDistance.getSpeed().endsWith("f")) {
				throw new NumberFormatException();
			}
			speed = Double.parseDouble(stoppingDistance.getSpeed());
		} catch (NumberFormatException nfe) {
			errors.rejectValue("speed", "notANumber");
			return;
		}
		
		try {
			if (stoppingDistance.getDeceleration().endsWith("d") || stoppingDistance.getDeceleration().endsWith("f")) {
				throw new NumberFormatException();
			}
			deceleration = Double.valueOf(stoppingDistance.getDeceleration());
		} catch (NumberFormatException nfe) {
			errors.rejectValue("deceleration", "notANumber");
			return;
		}
				
		if (speed <= 0.0) {
			errors.rejectValue("speed", "mustBeGreaterThanZero");
			return;
		}
		
		if (deceleration <= 0.0) {
			errors.rejectValue("deceleration", "mustBeGreaterThanZero");
			return;
		}
	}
	
}
