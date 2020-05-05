package ec.com.wolfdev.lembretes.modules.user.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ec.com.wolfdev.lembretes.core.security.CurrentUser;
import ec.com.wolfdev.lembretes.core.security.UserPrincipal;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.service.UserService;
import ec.com.wolfdev.lembretes.utils.Const;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "user", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('MASTER','GERENTE','PUBLIC')")
@Api(value = "API Module User")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('MODERADOR-FIND','USER-FIND')")
	@ApiOperation(value = "Retorna uma lista de users", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> getUsers() throws ServletException {
		return userService.getUsers();
	}

	@GetMapping(value = "search")
	@PreAuthorize("hasAnyAuthority('MODERADOR-FIND','USER-FIND')")
	@ApiOperation(value = "Retorna uma lista de Usuarios com filter", authorizations = {
			@Authorization(value = "accessToken") })
	public ResponseEntity<?> getUsersSearch(@RequestParam(required = false) Long personId,
			@RequestParam(required = false, defaultValue = "") String userName,
			@RequestParam(required = false, defaultValue = "") String personName,
			@RequestParam(required = false, defaultValue = "") String personLastName,
			@RequestParam(required = false) Long roleId, @RequestParam(required = false) Long userGroupId,
			@RequestParam(required = false) Boolean status, @RequestParam(required = false) Boolean isDeleted,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false, defaultValue = Const.SORT_FIELD_DEFAULT) String fieldName,
			@RequestParam(required = false, defaultValue = Const.SORT_DESC) String direction) throws ServletException {

		return userService.getUsersBySearch(personId, userName, personName, personLastName, roleId, userGroupId, status,
				isDeleted, startDate == null ? new Date(0) : startDate, endDate == null ? new Date() : endDate, page,
				size, fieldName, direction);
	}

	@GetMapping(path = "{id}")
	@PreAuthorize("hasAnyAuthority('MODERADOR-READ','USER-READ')")
	@ApiOperation(value = "Retorna um user unico", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> getUser(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return userService.getUser(id);
	}

	@GetMapping(value = "me")
	@PreAuthorize("hasAnyAuthority('MODERADOR-READ','USER-READ')")
	@ApiOperation(value = "Retorna o user autenticado", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> getUser(@CurrentUser UserPrincipal userPrincipal) throws ServletException {
		return userService.meUser(userPrincipal.getId());
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('MODERADOR-WRITE','USER-WRITE')")
	@ApiOperation(value = "Salva um user", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> createUser(@RequestBody(required = true) @Valid User user)
			throws ServletException, PgpException {
		return userService.saveUser(user, true);
	}

	@PutMapping(path = "{id}")
	@PreAuthorize("hasAnyAuthority('MODERADOR-WRITE','USER-WRITE')")
	@ApiOperation(value = "Atualiza um user", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> updateUser(@PathVariable(required = true, name = "id") Long id,
			@RequestBody(required = true) User user) throws ServletException, PgpException {
		user.setId(id);
		return userService.saveUser(user, false);
	}

	@DeleteMapping(path = "{id}")
	@PreAuthorize("hasAnyAuthority('MODERADOR-DELETE','MODERADOR-WRITE')")
	@ApiOperation(value = "Desativa um user", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> deleteUser(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return userService.deleteUser(id);
	}

	@PutMapping(value = "block/{id}")
	@PreAuthorize("hasAnyAuthority('MODERADOR-WRITE')")
	@ApiOperation(value = "Desabilita um user", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> blockUser(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return userService.blockUser(id);
	}
}
