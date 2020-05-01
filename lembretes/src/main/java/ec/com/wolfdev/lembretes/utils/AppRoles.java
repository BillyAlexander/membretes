package ec.com.wolfdev.lembretes.utils;

import lombok.Getter;
import lombok.Setter;

public enum AppRoles {
	PUBLIC(1L), GERENTE(2L), MASTER(3L);

	private @Setter @Getter Long role;

	private AppRoles(Long role) {
		this.role = role;
	}
}
