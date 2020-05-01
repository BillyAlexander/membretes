package ec.com.wolfdev.lembretes.modules.role_permission_module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.role_permission_module.entity.RolePermissionModule;
import ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas.PermissionModuleModel;
import ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas.PermissionModuleModelAssembler;
import ec.com.wolfdev.lembretes.modules.role_permission_module.repository.PermissionModuleRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;

@Service
public class PermissionModuleService extends BaseService<RolePermissionModule> {

	public PermissionModuleService() {
		super(RolePermissionModule.class);
	}

	@Autowired
	private PermissionModuleRepo permissionModuleRepo;

	@Autowired
	private PermissionModuleModelAssembler permissionModuleModelAssembler;

	public ResponseEntity<?> getPermission(Long id) {
		try {
			RolePermissionModule permission = permissionModuleRepo.findById(id).orElse(null);
			if (permission == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}
			PermissionModuleModel model = permissionModuleModelAssembler.toModel(permission);

			return new ResponseEntity<PermissionModuleModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
