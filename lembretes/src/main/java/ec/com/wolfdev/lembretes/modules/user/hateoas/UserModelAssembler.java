package ec.com.wolfdev.lembretes.modules.user.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas.CatalogAdminModelAssembler;
import ec.com.wolfdev.lembretes.modules.role.hateoas.RoleModelAssembler;
import ec.com.wolfdev.lembretes.modules.user.controller.UserController;
import ec.com.wolfdev.lembretes.modules.user.entity.User;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

	public UserModelAssembler() {
		super(UserController.class, UserModel.class);
	}

	@Autowired
	private CatalogAdminModelAssembler catalogAdminModelAssembler;

	@Autowired
	private RoleModelAssembler roleModelAssembler;

	@Override
	public UserModel toModel(User entity) {
		UserModel userModel = instantiateModel(entity);
		try {
			userModel.add(linkTo(methodOn(UserController.class).getUser(entity.getId())).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		userModel.setId(entity.getId());
		userModel.setCreationDate(entity.getCreationDate());
		userModel.setIsDeleted(entity.getIsDeleted());
		userModel.setStatus(entity.getStatus());
		userModel.setUserName(entity.getUserName());
		userModel.setUserGroup(catalogAdminModelAssembler.toModel(entity.getUserGroup()));
		userModel.setRole(roleModelAssembler.toModel(entity.getRole()));
		userModel.setProvider(entity.getProvider());
		return userModel;
	}

//	@Override
//	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
//
//		CollectionModel<UserModel> usersModel = super.toCollectionModel(entities);
//		try {
//			usersModel.add(linkTo(methodOn(UserController.class).getUsersSearch(personId, userName, personName, personLastName, roleId, userGroupId, status, isDeleted, startDate, endDate, page, size, fieldName, direction)(Const.PAGE_0_DEFAULT,
//					Const.PAGE_SIZE_DEFAULT, Const.SORT_FIELD_DEFAULT, Const.SORT_DESC)).withSelfRel());
//		} catch (ServletException e) {
//			e.printStackTrace();
//		}
//
//		return usersModel;
//	}

}
