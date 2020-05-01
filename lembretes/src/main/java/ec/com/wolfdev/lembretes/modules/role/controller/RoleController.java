package ec.com.wolfdev.lembretes.modules.role.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.modules.role.service.RoleService;
import ec.com.wolfdev.lembretes.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "role", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('MASTER')")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('MODERADOR-READ')")
	public ResponseEntity<?> getRole(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return roleService.getRole(id);
	}
}
