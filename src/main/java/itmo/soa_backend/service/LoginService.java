package itmo.soa_backend.service;

import itmo.soa_backend.BusinessLogicException;
import itmo.soa_backend.dto.LoginRequestDTO;
import itmo.soa_backend.dto.LoginResponseDTO;
import itmo.soa_backend.model.User;
import itmo.soa_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;
	
	public String login(LoginRequestDTO loginRequestDTO) {
		Optional<User> userOptional = userRepository.findByUsername(loginRequestDTO.login());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return loginRequestDTO.password();
		}
		return null;
	}
	
	public LoginResponseDTO createUser(LoginRequestDTO loginRequestDTO) {
		Optional<User> userOptional = userRepository.findByUsername(loginRequestDTO.login());
		if (userOptional.isPresent()) {
			throw new BusinessLogicException("Username is already taken!");
		}
		User user = new User(loginRequestDTO.login(), loginRequestDTO.password());
		userRepository.save(user);
		return new LoginResponseDTO(user.getUsername(), user.getPassword());
	}
}
