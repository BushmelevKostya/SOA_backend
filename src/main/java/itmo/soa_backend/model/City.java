package itmo.soa_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Long population;
	
	public City(String name, Long population) {
		this.name = name;
		this.population = population;
	}
	
	public City() {}
}
