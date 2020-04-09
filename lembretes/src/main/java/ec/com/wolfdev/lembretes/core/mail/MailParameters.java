package ec.com.wolfdev.lembretes.core.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class MailParameters {
	private @Getter @Setter String subject;

	private @Getter @Setter String content;

	private @Getter @Setter List<String> recipentTO = new ArrayList<>();

	private @Getter @Setter List<String> recipentCC = new ArrayList<>();

	private @Getter @Setter List<String> recipentCCO = new ArrayList<>();

	private @Getter @Setter Map<String, String> parameters = new HashMap<>();
}
