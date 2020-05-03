package ec.com.wolfdev.lembretes.modules.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.wolfdev.lembretes.modules.mail.entity.MailMessages;

public interface MailRepo extends JpaRepository<MailMessages, Long> {
	public MailMessages findByAcronym(String acronym);
}
