package ec.com.wolfdev.lembretes.application.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ec.com.wolfdev.lembretes.core.base.context.WolfDevBaseConfig;
import ec.com.wolfdev.lembretes.core.security.UserPrincipal;

public class LembretesBaseJpa implements WolfDevBaseConfig {

	@Override
	public Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			return userPrincipal.getId();
		}
		return -1L;
	}

	@Override
	public String getCurrentUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			// Find first matching
			GrantedAuthority firstResult = userPrincipal.getAuthorities().stream()
					.filter(p -> p.getAuthority().startsWith("ROLE")).findFirst().orElse(null);
			if (firstResult == null)
				return "";
			return firstResult.getAuthority();
		}
		return "";
	}

}
