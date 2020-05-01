package ec.com.wolfdev.lembretes.modules.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ec.com.wolfdev.lembretes.core.security.UserPrincipal;
import ec.com.wolfdev.lembretes.core.security.exception.OAuth2AuthenticationProcessingException;
import ec.com.wolfdev.lembretes.core.security.oauth2.user.OAuth2UserInfo;
import ec.com.wolfdev.lembretes.core.security.oauth2.user.OAuth2UserInfoFactory;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.repository.UserRepo;
import ec.com.wolfdev.lembretes.utils.AppRoles;
import ec.com.wolfdev.lembretes.utils.AuthProvider;
import ec.com.wolfdev.lembretes.utils.Const;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Optional<User> userOptional = userRepo.findByUserNameIgnoreCase(oAuth2UserInfo.getEmail());
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			if (!user.getProvider()
					.equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		User user = new User();

		user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oAuth2UserInfo.getId());
		user.setUserName(oAuth2UserInfo.getEmail());
		CatalogAdmin userG = new CatalogAdmin();
		userG.setId(Const.USER_GROUP_DEFAULT);
		user.setUserGroup(userG);
		Role role = new Role();
		role.setId(AppRoles.PUBLIC.getRole());
		user.setRole(role);

		return userRepo.save(user);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		// existingUser.setName(oAuth2UserInfo.getName());

		// existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
		// return userRepo.save(existingUser);
		return existingUser;
	}

}