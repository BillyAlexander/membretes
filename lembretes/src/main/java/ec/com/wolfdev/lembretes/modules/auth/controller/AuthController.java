package ec.com.wolfdev.lembretes.modules.auth.controller;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.core.security.jwtoken.TokenProvider;
import ec.com.wolfdev.lembretes.core.security.oauth2.payload.AuthResponse;
import ec.com.wolfdev.lembretes.core.security.oauth2.payload.LoginRequest;
import ec.com.wolfdev.lembretes.core.security.oauth2.payload.SignUpRequest;
import ec.com.wolfdev.lembretes.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value="API Module Authentication")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("login")
	@ApiOperation(value="Retorna um token para o user")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	@ApiOperation(value="Cadastra um novo user")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws ServletException {
		return userService.registerUser(signUpRequest);
	}

	@PostMapping("/passwordRecovery")
	@ApiOperation(value="Retorna uma mensagem email como uma nova senha")
	public ResponseEntity<?> passwordRecovery(@RequestBody LoginRequest loginRequest) throws ServletException {
		return userService.passwordRecovery(loginRequest.getUserName());
	}

}
