package ec.com.wolfdev.lembretes.modules.user.controller;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.service.UserService;
import ec.com.wolfdev.lembretes.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE+"user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getUsers() throws ServletException {
		return userService.getUsers();
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws ServletException {
		return userService.getUser(id);
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid User user) throws ServletException, PgpException {
		return userService.saveUser(user, true);
	}
	
	@PutMapping(path = "{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id,@RequestBody User user) throws ServletException, PgpException {
		user.setId(id);
		return userService.saveUser(user,false);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id ) throws ServletException {
		return userService.deleteUser(id);
	}
}
