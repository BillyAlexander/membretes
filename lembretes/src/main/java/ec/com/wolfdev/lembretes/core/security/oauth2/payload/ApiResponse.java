package ec.com.wolfdev.lembretes.core.security.oauth2.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
	private boolean success;
	private String message;
}
