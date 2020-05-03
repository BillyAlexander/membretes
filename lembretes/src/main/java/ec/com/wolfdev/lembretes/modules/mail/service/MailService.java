package ec.com.wolfdev.lembretes.modules.mail.service;

import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.core.mail.MailParameters;
import ec.com.wolfdev.lembretes.modules.mail.entity.MailMessages;
import ec.com.wolfdev.lembretes.modules.mail.repository.MailRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import lombok.Getter;

@Service
public class MailService extends BaseService<MailMessages> {

	public MailService() {
		super(MailMessages.class);
	}

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailRepo mailRepo;

	@Value("${app.main-path}")
	private @Getter String mainPath;

	public String replaceParams(Map<String, String> params, String content) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
		}
		return content;
	}

	public void send(MailParameters param) throws MessagingException, PgpException {
		String mail = (param.getRecipentTO().get(0) == null) ? null : param.getRecipentTO().get(0);
		if (mail == null) {
			mail = "";
			throw new PgpException(AppMessage.MSJ_ERR_MAIL);
		}

		MimeMessage message = mailSender.createMimeMessage();
		for (String address : param.getRecipentTO()) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
		}
		for (String address : param.getRecipentCC()) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
		}
		for (String address : param.getRecipentCCO()) {
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));
		}
		message.setSubject(param.getSubject());
		message.setContent(param.getContent(), "text/html");
		mailSender.send(message);
	}

	public Boolean sendMailWihTemplate(MailParameters mailParameters, String template, Map<String, String> params)
			throws MessagingException, PgpException {

		MailMessages mail = mailRepo.findByAcronym(template);
		if (mail != null) {
			params.put("mainPath", getMainPath());
			String content = replaceParams(params, mail.getContent());
			mailParameters.setSubject(mail.getSubject());
			mailParameters.setContent(content);
			send(mailParameters);
			return true;
		}

		return false;
	}

}
