package ec.com.wolfdev.lembretes.core.security.oauth2.payload;

import ec.com.wolfdev.lembretes.core.security.config.Const;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
	private String accessToken;
	private String tokenType = Const.TOKEN_TYPE;

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
