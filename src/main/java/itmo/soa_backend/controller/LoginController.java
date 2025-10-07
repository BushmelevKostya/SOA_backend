package itmo.soa_backend.controller;

import itmo.soa_backend.BusinessLogicException;
import itmo.soa_backend.dto.LoginRequestDTO;
import itmo.soa_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		try {
			return ResponseEntity.ok(loginService.login(loginRequestDTO));
		} catch (BusinessLogicException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody LoginRequestDTO loginRequestDTO) {
		try {
			return ResponseEntity.ok(loginService.createUser(loginRequestDTO));
		} catch (BusinessLogicException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
}
