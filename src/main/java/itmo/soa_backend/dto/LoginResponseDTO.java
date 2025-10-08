package itmo.soa_backend.dto;

public record LoginResponseDTO(
		String token,
		String username,
		String message
) {
	public static LoginResponseDTO success(String token, String username) {
		return new LoginResponseDTO(token, username, "Login successful");
	}
	
	public static LoginResponseDTO error(String message) {
		return new LoginResponseDTO(null, null, message);
	}
}