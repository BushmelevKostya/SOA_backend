package itmo.soa_backend.controller;

import itmo.soa_backend.BusinessLogicException;
import itmo.soa_backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CityController {
	@Autowired
	private CityService cityService;
	
	@GetMapping("/data")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getCities() {
		try {
			return ResponseEntity.ok(cityService.getCities());
		} catch (BusinessLogicException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
}
