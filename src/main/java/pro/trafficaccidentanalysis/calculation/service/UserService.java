package pro.trafficaccidentanalysis.calculation.service;

import pro.trafficaccidentanalysis.calculation.model.User;
import pro.trafficaccidentanalysis.calculation.web.dto.UserRegistrationDto;

public interface UserService {
	
	public User save(UserRegistrationDto registrationDto);
}
