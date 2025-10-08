package itmo.soa_backend.dto;

import itmo.soa_backend.model.City;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;

public record CityResponseDTO(List<City> cities) {
	public List<City> getSafeCities() {
		return cities.stream()
				.map(city -> {
					City safeCity = new City();
					safeCity.setId(city.getId());
					safeCity.setName(HtmlUtils.htmlEscape(city.getName()));
					safeCity.setPopulation(city.getPopulation());
					return safeCity;
				})
				.collect(Collectors.toList());
	}
}