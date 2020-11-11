package pro.trafficaccidentanalysis.calculation.web.dto.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pro.trafficaccidentanalysis.calculation.model.User;
import pro.trafficaccidentanalysis.calculation.web.dto.UserRegistrationDto;

@Component
public class UserRegistrationDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRegistrationDto user = (UserRegistrationDto) target;

		if (!user.getPassword().equals(user.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "passwordsDontMatch");
		}
	}
	
	public void validate(Object target, Errors errors, User user) {
		UserRegistrationDto userDto = (UserRegistrationDto) target;

		if (user != null) {
			errors.rejectValue("email", "emailAlreadyExists");
			return;
		}
		
		if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "passwordsDontMatch");
		}
	}

}
