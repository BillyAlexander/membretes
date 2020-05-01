package ec.com.wolfdev.lembretes.core.security.oauth2.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
	@NotBlank
	@Email
	private String userName;

	@NotBlank
	private String password;
}
