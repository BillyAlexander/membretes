package ec.com.wolfdev.lembretes.core.base.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ErrorControl {
	private @Getter @Setter String error;
	private @Getter @Setter int statusCode;
	private @Getter @Setter boolean isCustom;
}