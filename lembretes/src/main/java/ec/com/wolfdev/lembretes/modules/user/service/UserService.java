package ec.com.wolfdev.lembretes.modules.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.message.MessageControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.user.controller.UserSearch;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.entity.lite.UserLite;
import ec.com.wolfdev.lembretes.modules.user.repository.UserRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import ec.com.wolfdev.lembretes.utils.AppMethods;
import ec.com.wolfdev.lembretes.utils.AppRoles;
import ec.com.wolfdev.lembretes.utils.Const;

@Service
public class UserService extends BaseService<User> {

	public UserService() {
		super(User.class);
	}

	@Autowired
	private UserRepo userRepo;

	@Transactional
	public ResponseEntity<?> getUsers() {
		try {
			return new ResponseEntity<List<UserLite>>(
					userRepo.findByIsDeletedFalseAndStatusTrue(AppMethods.sortFound("creationDate", Const.SORT_DESC)),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getUsersBySearch(UserSearch search) {
		try {
			return new ResponseEntity<Page<UserLite>>(userRepo.findUserBySearch(search.getPersonId(),
					search.getUserName(), search.getPersonDocumentId(), search.getPersonName(),
					search.getPersonLastName(), search.getRoleId(), search.getUserGroupId(), search.getStatus(),
					search.isDeleted, search.getStartDate(), search.getEndDate(), search.getPaginate().getPaginate()), HttpStatus.OK);
		} catch (Exception err) {
			err.printStackTrace();
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getUser(Long id) {
		try {
			User found = userRepo.findById(id).orElse(null);
			if (found == null)
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else if (found.getIsDeleted())
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<User>(found, HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> deleteUser(Long id) {
		try {
			User found = userRepo.findById(id).orElse(null);
			if (found == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}

			if (found.getRole().getName().equals(AppRoles.MASTER.name())
					|| found.getRole().getName().equals(AppRoles.GERENTE.name())) {
				return new ResponseEntity<ErrorControl>(new ErrorControl(
						String.format(AppMessage.MSJ_NOT_DELETE_MODERATOR_USER,
								AppRoles.GERENTE.name() + ", " + AppRoles.MASTER.name()),
						HttpStatus.NOT_IMPLEMENTED.value(), true), HttpStatus.NOT_IMPLEMENTED);
			}
			found.setIsDeleted(true);
			userRepo.save(found);
			return new ResponseEntity<MessageControl>(
					new MessageControl(AppMessage.MSJ_DELETE_INFORMATION, HttpStatus.OK.value(), true), HttpStatus.OK);
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
			found.setIsDeleted(rplUser.getIsDeleted() == null ? found.getIsDeleted() : rplUser.getIsDeleted());
		}

		return found;
	}
}
