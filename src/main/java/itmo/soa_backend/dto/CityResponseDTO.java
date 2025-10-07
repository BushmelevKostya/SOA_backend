package itmo.soa_backend.dto;

import itmo.soa_backend.model.City;

import java.util.List;

public record CityResponseDTO(List<City> cities) {
}
