package ec.com.wolfdev.lembretes.modules.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.wolfdev.lembretes.core.security.UserPrincipal;
import ec.com.wolfdev.lembretes.core.security.UserToFilter;
import ec.com.wolfdev.lembretes.core.security.exception.ResourceNotFoundException;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserNameIgnoreCaseAndIsDeletedFalseAndStatusTrue(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with userName : " + username));

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		List<UserToFilter> userToken = userRepo.findUserByIdInToken(id);
		if(userToken.size()<1)
			throw new ResourceNotFoundException("User", "id", id);

        //User user=userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return UserPrincipal.create(userToken);
	}
}
