package itmo.soa_backend;

import java.io.Serial;

public class BusinessLogicException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;
	
	public BusinessLogicException(String message) {
		super(message);
	}
}