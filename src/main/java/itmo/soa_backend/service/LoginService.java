package itmo.soa_backend.service;

import itmo.soa_backend.BusinessLogicException;
import itmo.soa_backend.config.JwtAuthService;
import itmo.soa_backend.dto.LoginRequestDTO;
import itmo.soa_backend.dto.LoginResponseDTO;
import itmo.soa_backend.model.User;
import itmo.soa_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthService jwtAuthService;
	
	public LoginService(UserRepository userRepository,
	                    PasswordEncoder passwordEncoder,
	                    JwtAuthService jwtAuthService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtAuthService = jwtAuthService;
	}
	
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Optional<User> userOptional = userRepository.findByUsername(loginRequestDTO.login());
		
		if (userOptional.isPresent()) {
			User user = userOptional.get();

			if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
				String token = jwtAuthService.generateToken(user.getUsername());
				return LoginResponseDTO.success(token, user.getUsername());
			} else {
				return LoginResponseDTO.error("Invalid password");
			}
		}
		
		return LoginResponseDTO.error("User not found");
	}
	
	@Transactional
	public LoginResponseDTO createUser(LoginRequestDTO loginRequestDTO) {
		Optional<User> userOptional = userRepository.findByUsername(loginRequestDTO.login());
		
		if (userOptional.isPresent()) {
			throw new BusinessLogicException("Username is already taken!");
		}
		
		String encodedPassword = passwordEncoder.encode(loginRequestDTO.password());
		User user = new User(loginRequestDTO.login(), encodedPassword);
		userRepository.save(user);
		
		String token = jwtAuthService.generateToken(user.getUsername());
		return LoginResponseDTO.success(token, user.getUsername());
	}
	
}