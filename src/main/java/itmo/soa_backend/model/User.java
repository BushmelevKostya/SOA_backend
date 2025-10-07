package itmo.soa_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User() {}
}
