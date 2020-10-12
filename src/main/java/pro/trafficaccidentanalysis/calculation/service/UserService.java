package pro.trafficaccidentanalysis.calculation.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import pro.trafficaccidentanalysis.calculation.model.User;
import pro.trafficaccidentanalysis.calculation.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	
	public User save(UserRegistrationDto registrationDto);
	
	public User findByEmail(String email);
}
