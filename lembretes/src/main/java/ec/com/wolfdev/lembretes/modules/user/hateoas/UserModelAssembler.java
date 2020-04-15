package ec.com.wolfdev.lembretes.modules.user.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.ServletException;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.person.controller.PersonController;
import ec.com.wolfdev.lembretes.modules.user.controller.UserController;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.utils.Const;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

	public UserModelAssembler() {
		super(UserController.class, UserModel.class);
	}

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
		userModel.setPassword(entity.getPassword());
		userModel.setUserName(entity.getUserName());

		return userModel;
	}

	@Override
	public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {

		CollectionModel<UserModel> usersModel = super.toCollectionModel(entities);
		try {
			usersModel.add(linkTo(methodOn(PersonController.class).getPeople(Const.PAGE_0_DEFAULT,
					Const.PAGE_SIZE_DEFAULT, Const.SORT_FIELD_DEFAULT, Const.SORT_DESC)).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}

		return usersModel;
	}
}
