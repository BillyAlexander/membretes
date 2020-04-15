package ec.com.wolfdev.lembretes.modules.user.controller;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.service.UserService;
import ec.com.wolfdev.lembretes.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getUsers() throws ServletException {
		return userService.getUsers();
	}

	@GetMapping(value = "search")
	public ResponseEntity<?> getUsersSearch(@RequestParam(required = false) Long personId,
			@RequestParam(required = false, defaultValue = "") String userName,
			@RequestParam(required = false, defaultValue = "") String personDocumentId,
			@RequestParam(required = false, defaultValue = "") String personName,
			@RequestParam(required = false, defaultValue = "") String personLastName,
			@RequestParam(required = false) Long roleId, @RequestParam(required = false) Long userGroupId,
			@RequestParam(required = false) Boolean status, @RequestParam(required = false) Boolean isDeleted,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false, defaultValue = Const.SORT_FIELD_DEFAULT) String fieldName,
			@RequestParam(required = false, defaultValue = Const.SORT_DESC) String direction) throws ServletException {

		return userService.getUsersBySearch(personId, userName, personDocumentId, personName, personLastName, roleId,
				userGroupId, status, isDeleted, startDate == null ? new Date(0) : startDate,
				endDate == null ? new Date() : endDate, page, size, fieldName, direction);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws ServletException {
		return userService.getUser(id);
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody(required = true) @Valid User user)
			throws ServletException, PgpException {
		return userService.saveUser(user, true);
	}

	@PutMapping(path = "{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody(required = true) User user)
			throws ServletException, PgpException {
		user.setId(id);
		return userService.saveUser(user, false);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return userService.deleteUser(id);
	}
}
