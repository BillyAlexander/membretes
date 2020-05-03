package ec.com.wolfdev.lembretes.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import lombok.Getter;

public class UserPrincipal implements OAuth2User, UserDetails {
	private static final long serialVersionUID = 1L;

	private @Getter Long id;
	private @Getter String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	// private @Getter Boolean status;

	public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {

		List<GrantedAuthority> authorities = new ArrayList<>();
		Role role = user.getRole();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		role.getRolePermissionModules().stream()
				.map(p -> new SimpleGrantedAuthority(p.getModule().getName() + "-" + p.getPermission().getName()))
				.forEach(authorities::add);

		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println(grantedAuthority.getAuthority());
		}

		return new UserPrincipal(user.getId(), user.getUserName(), user.getPassword(), authorities);
	}

	public static UserPrincipal create(User user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return String.valueOf(id);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
