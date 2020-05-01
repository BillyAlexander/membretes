package ec.com.wolfdev.lembretes.modules.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.modules.role.hateoas.RoleModel;
import ec.com.wolfdev.lembretes.modules.role.hateoas.RoleModelAssembler;
import ec.com.wolfdev.lembretes.modules.role.repository.RoleRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;

@Service
public class RoleService extends BaseService<Role> {

	public RoleService() {
		super(Role.class);
	}

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private RoleModelAssembler roleModelAssembler;

	public ResponseEntity<?> getRole(Long id) {
		try {
			Role role = roleRepo.findById(id).orElse(null);
			if (role == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}
			RoleModel roleModel = roleModelAssembler.toModel(role);

			return new ResponseEntity<RoleModel>(roleModel, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
