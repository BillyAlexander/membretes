package ec.com.wolfdev.lembretes.modules.role.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.ServletException;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.role.controller.RoleController;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;

@Component
public class RoleModelAssembler extends RepresentationModelAssemblerSupport<Role, RoleModel> {

	public RoleModelAssembler() {
		super(RoleController.class, RoleModel.class);
	}

	@Override
	public RoleModel toModel(Role entity) {
		RoleModel model = instantiateModel(entity);
		model.setId(entity.getId());
		model.setDescription(entity.getDescription());
		model.setName(entity.getName());
		try {
			model.add(linkTo(methodOn(RoleController.class).getRole(entity.getId())).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return model;
	}
}
