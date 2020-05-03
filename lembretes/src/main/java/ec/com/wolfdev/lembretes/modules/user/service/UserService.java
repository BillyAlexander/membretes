package ec.com.wolfdev.lembretes.modules.user.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ec.com.wolfdev.lembretes.core.base.context.WolfDevBase;
import ec.com.wolfdev.lembretes.core.base.context.WolfDevBaseContextHolder;
import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.message.MessageControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.core.mail.MailParameters;
import ec.com.wolfdev.lembretes.core.security.exception.BadRequestException;
import ec.com.wolfdev.lembretes.core.security.exception.ResourceNotFoundException;
import ec.com.wolfdev.lembretes.core.security.oauth2.payload.ApiResponse;
import ec.com.wolfdev.lembretes.core.security.oauth2.payload.SignUpRequest;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.mail.service.MailService;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas.PermissionModuleModelAssembler;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.hateoas.UserModel;
import ec.com.wolfdev.lembretes.modules.user.hateoas.UserModelAssembler;
import ec.com.wolfdev.lembretes.modules.user.repository.UserRepo;
import ec.com.wolfdev.lembretes.utils.AcronyMail;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import ec.com.wolfdev.lembretes.utils.AppMethods;
import ec.com.wolfdev.lembretes.utils.AppRoles;
import ec.com.wolfdev.lembretes.utils.AuthProvider;
import ec.com.wolfdev.lembretes.utils.Const;

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
	private PermissionModuleModelAssembler permissionModuleModelAssembler;

	@Autowired
	private PagedResourcesAssembler<User> pagedResourcesAssembler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private WolfDevBase wolfDev = WolfDevBaseContextHolder.getContext().getBean(WolfDevBase.class);

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
	public ResponseEntity<?> getUsersBySearch(Long personId, String userName, String personName, String personLastName,
			Long roleId, Long userGroupId, Boolean status, Boolean isDeleted, Date startDate, Date endDate, int page,
			int size, String fieldName, String direction) {
		PagedModel<UserModel> users = pagedResourcesAssembler.toModel(userRepo.findUserBySearch(personId, userName,
				personName, personLastName, roleId, userGroupId, status, isDeleted, startDate, endDate,
				PageRequest.of(page, size, AppMethods.sortFound(fieldName, direction))), userModelAssembler);
		try {
			return new ResponseEntity<PagedModel<UserModel>>(users, HttpStatus.OK);
		} catch (Exception err) {
			err.printStackTrace();
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getUser(Long id) {
		try {
			User user = userRepo.findById(id).orElse(null);

			if (user == null)
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else if (!user.getStatus() || user.getIsDeleted()) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, user.getUserName()),
								HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			} else {
				UserModel userModel = userModelAssembler.toModel(user);
				userModel.getRole().setPermissionModules(
						permissionModuleModelAssembler.ToList(user.getRole().getRolePermissionModules()));
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

			User user = userRepo.findById(id).orElse(null);

			if (user == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			} else {
				if (!verifyRoleUser(user.getRole().getId(), user.getId(), false, true))
					return new ResponseEntity<ErrorControl>(
							new ErrorControl(AppMessage.MSJ_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), true),
							HttpStatus.UNAUTHORIZED);

				user.setIsDeleted(!user.getIsDeleted());
				user = userRepo.save(user);

				String msg = AppMessage.MSJ_DELETE_INFORMATION;
				if (user.getIsDeleted())
					msg = String.format(msg, "desactivada");
				else
					msg = String.format(msg, "activada");

				return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.OK.value(), true),
						HttpStatus.OK);
			}

		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> blockUser(Long id) {
		try {

			User user = userRepo.findById(id).orElse(null);

			if (user == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			} else {

				if (!verifyRoleUser(user.getRole().getId(), user.getId(), false, true))
					return new ResponseEntity<ErrorControl>(
							new ErrorControl(AppMessage.MSJ_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), true),
							HttpStatus.UNAUTHORIZED);

				user.setStatus(!user.getStatus());
				user = userRepo.save(user);

				String msg = AppMessage.MSJ_BLOCKED_INFORMATION;
				if (user.getStatus())
					msg = String.format(msg, "habilitada");
				else
					msg = String.format(msg, "deshabilitada");

				return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.OK.value(), true),
						HttpStatus.OK);
			}

		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> saveUser(User user, boolean isCreate) throws PgpException {
		// Verify email";
		if (userRepo.existsByUserName(user.getUserName())) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(AppMessage.MSJ_SIGNUP_EMAIL_EXISTS + " " + user.getUserName(),
							HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// save user
		try {

			String msg = "";
			if (isCreate) {
				msg = AppMessage.MSJ_CREATE_INFORMATION;
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setProvider(AuthProvider.local);
			} else {
				// User found =
				// userRepo.findByUserNameIgnoreCaseAndIsDeletedFalseAndStatusTrue(user.getUserName());
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

			if (!verifyRoleUser(user.getRole().getId(), user.getId(), isCreate, false))
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), true),
						HttpStatus.UNAUTHORIZED);

			userRepo.save(user);

			return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.CREATED.value(), true),
					HttpStatus.CREATED);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> meUser(Long id) {
		try {
			UserModel userModel = userRepo.findById(id).map(userModelAssembler::toModel)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
			return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
		try {
			if (userRepo.existsByUserName(signUpRequest.getUserName())) {
				throw new BadRequestException(AppMessage.MSJ_SIGNUP_EMAIL_EXISTS + " " + signUpRequest.getUserName());
			}

			if (!verifyRoleUser(AppRoles.PUBLIC.getRole(), 0L, true, false))
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), true),
						HttpStatus.UNAUTHORIZED);

			// Creating user's account
			User user = new User();
			user.setName(signUpRequest.getName());
			user.setLastName(signUpRequest.getLastName());
			user.setUserName(signUpRequest.getUserName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setProvider(AuthProvider.local);

			// group
			CatalogAdmin group = new CatalogAdmin();
			group.setId(Const.USER_GROUP_DEFAULT);
			user.setUserGroup(group);

			// role
			Role role = new Role();
			role.setId(AppRoles.PUBLIC.getRole());
			user.setRole(role);

			User result = userRepo.save(user);

			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
					.buildAndExpand(result.getId()).toUri();

			return ResponseEntity.created(location).body(new ApiResponse(true, AppMessage.MSJ_SIGNUP_OK));
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> passwordRecovery(String email) {
		try {
			User user = userRepo.findByUserNameIgnoreCase(email)
					.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
			if (!user.getStatus() || user.getIsDeleted()) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, user.getUserName()),
								HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}

			String randomPassword = AppMethods.randomPassword();
			user.setPassword(passwordEncoder.encode(randomPassword));

			MailParameters mailParameters = new MailParameters();
			mailParameters.setRecipentTO(Arrays.asList(user.getUserName()));

			Map<String, String> params = new HashMap<>();
			params.put("password", randomPassword);

			try {
				mailService.sendMailWihTemplate(mailParameters, AcronyMail.RECOVERY_PASSWORD, params);
				userRepo.save(user);
			} catch (Exception e) {
				throw e;
			}

			return new ResponseEntity<MessageControl>(
					new MessageControl(String.format(AppMessage.MSJ_PASS_RECOVERY, user.getUserName()),
							HttpStatus.OK.value(), true),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public User userReplace(User rplUser) {
		User found = userRepo.findById(rplUser.getId()).orElse(null);
		if (found != null) {
			found.setUserName(rplUser.getUserName() == null ? found.getUserName() : rplUser.getUserName());
			found.setPassword(rplUser.getPassword() == null ? found.getPassword()
					: passwordEncoder.encode(rplUser.getPassword()));
			found.setRole(rplUser.getRole() == null ? found.getRole() : rplUser.getRole());
			found.setUserGroup(rplUser.getUserGroup() == null ? found.getUserGroup() : rplUser.getUserGroup());
			found.setAddress(rplUser.getAddress() == null ? found.getAddress() : rplUser.getAddress());
			found.setCellPhone(rplUser.getCellPhone() == null ? found.getCellPhone() : rplUser.getCellPhone());
			found.setLastName(rplUser.getLastName() == null ? found.getLastName() : rplUser.getLastName());
			found.setName(rplUser.getName() == null ? found.getName() : rplUser.getName());
			found.setPhone(rplUser.getPhone() == null ? found.getPhone() : rplUser.getPhone());
		}
		return found;
	}

	public Boolean verifyRoleUser(Long userRoleId, Long userId, Boolean isCreate, Boolean toBlock) {
		Boolean result = false;
		switch (wolfDev.getConfig().getCurrentUserRole()) {
		case "ROLE_MASTER":
			if (isCreate) {
				if (userRoleId.equals(AppRoles.MASTER.getRole()) || userRoleId.equals(AppRoles.GERENTE.getRole()))
					result = true;
			}
			if (toBlock)
				if (!(userRoleId.equals(AppRoles.MASTER.getRole())
						&& userId.equals(wolfDev.getConfig().getCurrentUserId())))
					result = true;
			break;
		case "ROLE_GERENTE":

			if (toBlock) {
				if ((userRoleId.equals(AppRoles.GERENTE.getRole())
						&& !userId.equals(wolfDev.getConfig().getCurrentUserId()))
						|| userRoleId.equals(AppRoles.PUBLIC.getRole()))
					result = true;
			} else {
				if (userRoleId.equals(AppRoles.GERENTE.getRole()))
					result = true;
			}

			break;
		case "ROLE_PUBLIC":
			if (!toBlock)
				result = userRoleId.equals(AppRoles.PUBLIC.getRole());
			break;
		default:
			result = false;
			break;
		}

		return result;
	}

}
