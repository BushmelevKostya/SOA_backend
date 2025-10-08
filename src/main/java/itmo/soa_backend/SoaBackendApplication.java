package itmo.soa_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class SoaBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SoaBackendApplication.class, args);
	}
	
}
