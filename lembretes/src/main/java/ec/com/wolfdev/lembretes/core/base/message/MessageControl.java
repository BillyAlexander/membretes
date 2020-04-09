package ec.com.wolfdev.lembretes.core.base.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MessageControl {
	private @Getter @Setter String error;
	private @Getter @Setter boolean statusCode;
	private @Getter @Setter boolean isCustom;
}
