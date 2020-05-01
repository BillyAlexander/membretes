package ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas.CatalogAdminModelAssembler;
import ec.com.wolfdev.lembretes.modules.role.hateoas.RoleModelAssembler;
import ec.com.wolfdev.lembretes.modules.role_permission_module.controller.PermissionModuleController;
import ec.com.wolfdev.lembretes.modules.role_permission_module.entity.RolePermissionModule;

@Component
public class PermissionModuleModelAssembler
		extends RepresentationModelAssemblerSupport<RolePermissionModule, PermissionModuleModel> {

	public PermissionModuleModelAssembler() {
		super(PermissionModuleController.class, PermissionModuleModel.class);
	}

	@Autowired
	private CatalogAdminModelAssembler catalogAdminModelAssembler;

	@Autowired
	private RoleModelAssembler roleModelAssembler;

	@Override
	public PermissionModuleModel toModel(RolePermissionModule entity) {
		PermissionModuleModel model = instantiateModel(entity);
		model.setId(entity.getId());
		model.setModule(catalogAdminModelAssembler.toModel(entity.getModule()));
		model.setPermission(catalogAdminModelAssembler.toModel(entity.getPermission()));
		model.setRole(roleModelAssembler.toModel(entity.getRole()));

		try {
			model.add(
					linkTo(methodOn(PermissionModuleController.class).getPermission(entity.getId())).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}

		return model;
	}
	
	public List<PermissionModuleModel> ToList(List<RolePermissionModule> entities) {

		List<PermissionModuleModel> result = new ArrayList<>();

		for (RolePermissionModule entity : entities) {
			result.add(this.toModel(entity));
		}
		return result;
	}

}
