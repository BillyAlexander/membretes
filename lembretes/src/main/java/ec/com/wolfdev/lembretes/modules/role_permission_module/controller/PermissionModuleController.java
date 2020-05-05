package ec.com.wolfdev.lembretes.modules.role_permission_module.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.modules.role_permission_module.service.PermissionModuleService;
import ec.com.wolfdev.lembretes.utils.Const;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "permission", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('MASTER')")
@Api(value = "API Module PermissionModule")
public class PermissionModuleController {

	@Autowired
	private PermissionModuleService permissionModuleService;

	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('MODERADOR-READ')")
	@ApiOperation(value = "Retorna um PermissionModule", authorizations = { @Authorization(value = "accessToken") })
	public ResponseEntity<?> getPermission(@PathVariable(required = true, name = "id") Long id)
			throws ServletException {
		return permissionModuleService.getPermission(id);
	}

}
