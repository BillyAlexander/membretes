package ec.com.wolfdev.lembretes.core.base.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MessageControl {
	private @Getter @Setter String message;
	private @Getter @Setter int statusCode;
	private @Getter @Setter Boolean isCustom = true;
}
