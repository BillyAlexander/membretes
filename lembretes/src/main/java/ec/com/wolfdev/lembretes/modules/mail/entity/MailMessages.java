package ec.com.wolfdev.lembretes.modules.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA_COMMONS, name = "mail", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "acronym" }, name = "mail_acronym_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
public class MailMessages extends BaseEntity {
	@Column(columnDefinition = "VARCHAR")
	private String acronym;

	@Column(nullable = false, columnDefinition = "VARCHAR")
	private String subject;

	@Column(nullable = false, columnDefinition = "VARCHAR")
	private String content;
}
