package ec.com.wolfdev.lembretes.core.mail;

import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import lombok.Getter;

public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${lembretes.main-path}")
	private @Getter String mainPath;	
	
	public String replaceParams(Map<String, String> params, String content) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
		}
		return content;
	}
	
	public void send(MailParameters param) throws MessagingException, PgpException {
		String mail = (param.getRecipentTO().get(0)==null) ? null:param.getRecipentTO().get(0);
		if(mail == null) {
			mail = "";
			throw new PgpException(AppMessage.ERR_MSJ_MAIL);
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

			params.put("mainPath", getMainPath());
			String content = replaceParams(params, "MailContent.valueOf(template).getContent()");
			mailParameters.setSubject("mailSubject.valueOf(template).getSubject()");
			mailParameters.setContent("content");
			send(mailParameters);
			return true;

	}	
}
