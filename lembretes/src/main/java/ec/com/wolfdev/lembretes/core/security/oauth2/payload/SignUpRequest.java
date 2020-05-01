package ec.com.wolfdev.lembretes.core.security.oauth2.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignUpRequest {

	// person
	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	@Size(max = 20)
	private String lastName;

	// User
	@Email
	@NotBlank
	@Size(max = 60)
	private String userName;

	@NotBlank
	@Size(max = 200)
	private String password;

}
