package itmo.soa_backend.service;

import itmo.soa_backend.dto.CityResponseDTO;
import itmo.soa_backend.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	
	public CityResponseDTO getCities() {
		return new CityResponseDTO(cityRepository.findAll());
	}
}
