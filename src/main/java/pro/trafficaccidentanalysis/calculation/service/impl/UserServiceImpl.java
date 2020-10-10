package pro.trafficaccidentanalysis.calculation.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.trafficaccidentanalysis.calculation.model.Role;
import pro.trafficaccidentanalysis.calculation.model.User;
import pro.trafficaccidentanalysis.calculation.repository.UserRepository;
import pro.trafficaccidentanalysis.calculation.service.UserService;
import pro.trafficaccidentanalysis.calculation.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getName(), registrationDto.getEmail(), registrationDto.getPassword(), true,
				Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

}
