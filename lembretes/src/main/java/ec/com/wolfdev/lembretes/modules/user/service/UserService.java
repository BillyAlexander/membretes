package ec.com.wolfdev.lembretes.modules.user.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.message.MessageControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.hateoas.UserModel;
import ec.com.wolfdev.lembretes.modules.user.hateoas.UserModelAssembler;
import ec.com.wolfdev.lembretes.modules.user.repository.UserRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import ec.com.wolfdev.lembretes.utils.AppMethods;

@Service
public class UserService extends BaseService<User> {

	public UserService() {
		super(User.class);
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserModelAssembler userModelAssembler;

	@Autowired
	private PagedResourcesAssembler<User> pagedResourcesAssembler;

	@Transactional
	public ResponseEntity<?> getUsers() {
		try {
			return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getUsersBySearch(Long personId, String userName, String personDocumentId,
			String personName, String personLastName, Long roleId, Long userGroupId, Boolean status, Boolean isDeleted,
			Date startDate, Date endDate, int page, int size, String fieldName, String direction) {
		try {
			return new ResponseEntity<PagedModel<UserModel>>(pagedResourcesAssembler.toModel(
					userRepo.findUserBySearch(personId, userName, personDocumentId, personName, personLastName, roleId,
							userGroupId, status, isDeleted, startDate, endDate,
							PageRequest.of(page, size, AppMethods.sortFound(fieldName, direction))),
					userModelAssembler), HttpStatus.OK);
		} catch (Exception err) {
			err.printStackTrace();
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getUser(Long id) {
		try {
			UserModel userModel = userRepo.findById(id).map(userModelAssembler::toModel).orElse(null);

			if (userModel == null)
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else if (!userModel.getStatus() || userModel.getIsDeleted()) {
				return new ResponseEntity<ErrorControl>(new ErrorControl(
						String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, userModel.getUserName()),
						HttpStatus.NOT_FOUND.value(), true), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
			}
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> deleteUser(Long id) {
		try {
			return userRepo.findById(id).map(found -> {
				found.setIsDeleted(true);
				return userRepo.save(found);
			}).orElse(null) == null
					? new ResponseEntity<ErrorControl>(
							new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
							HttpStatus.NOT_FOUND)
					: new ResponseEntity<MessageControl>(
							new MessageControl(AppMessage.MSJ_DELETE_INFORMATION, HttpStatus.OK.value(), true),
							HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> saveUser(User user, boolean isCreate) throws PgpException {
		// String randomPassword="";
		if (isCreate) {
			User found = userRepo.findByUserNameIgnoreCaseAndIsDeletedFalseAndStatusTrue(user.getUserName());
			if (found != null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_SIGNUP_EMAIL_EXISTS + " " + user.getUserName(),
								HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		// save user
		try {
			String msg = "";
			if (isCreate) {
				msg = AppMessage.MSJ_CREATE_INFORMATION;
			} else {
				user = userReplace(user);
				if (user == null) {
					msg = AppMessage.MSJ_NOT_FOUND_INFORMATION;
					return new ResponseEntity<ErrorControl>(new ErrorControl(msg, HttpStatus.NOT_FOUND.value(), true),
							HttpStatus.NOT_FOUND);
				}
				if (user.getIsDeleted() || !user.getStatus()) {
					return new ResponseEntity<ErrorControl>(new ErrorControl(
							String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, user.getUserName()),
							HttpStatus.NOT_FOUND.value(), true), HttpStatus.NOT_FOUND);
				}
				msg = AppMessage.MSJ_UPDATE_INFORMATION;
			}
			userRepo.save(user);

			return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.OK.value(), true),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public User userReplace(User rplUser) {
		User found = userRepo.findById(rplUser.getId()).orElse(null);
		if (found != null) {
			found.setUserName(rplUser.getUserName() == null ? found.getUserName() : rplUser.getUserName());
			found.setPassword(rplUser.getPassword() == null ? found.getPassword() : rplUser.getPassword());
			found.setPerson(rplUser.getPerson() == null ? found.getPerson() : rplUser.getPerson());
			found.setRole(rplUser.getRole() == null ? found.getRole() : rplUser.getRole());
			found.setUserGroup(rplUser.getUserGroup() == null ? found.getUserGroup() : rplUser.getUserGroup());
		}

		return found;
	}
}
