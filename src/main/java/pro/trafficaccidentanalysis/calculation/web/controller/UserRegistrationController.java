package pro.trafficaccidentanalysis.calculation.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pro.trafficaccidentanalysis.calculation.model.User;
import pro.trafficaccidentanalysis.calculation.service.UserService;
import pro.trafficaccidentanalysis.calculation.web.dto.UserRegistrationDto;
import pro.trafficaccidentanalysis.calculation.web.dto.validation.UserRegistrationDtoValidator;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;
	private UserRegistrationDtoValidator userRegistrationDtoValidator;

	@Autowired
	public UserRegistrationController(UserService userService, UserRegistrationDtoValidator userRegistrationDtoValidator) {
		this.userService = userService;
		this.userRegistrationDtoValidator = userRegistrationDtoValidator;
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserRegistrationDto());
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto,
			BindingResult bindingResult) {
		User tempUser = userService.findByEmail(registrationDto.getEmail());

		userRegistrationDtoValidator.validate(registrationDto, bindingResult, tempUser);
		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
