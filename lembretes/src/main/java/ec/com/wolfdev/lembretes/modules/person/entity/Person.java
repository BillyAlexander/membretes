package ec.com.wolfdev.lembretes.modules.person.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "person", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "document_id" }, name = "person_document_id_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Person extends BaseEntity {

	@NotBlank
	@Size(max=13)
	@Column(name = "document_id", nullable = false, columnDefinition = "VARCHAR(13)")
	private @Getter @Setter String documentId;	

	@NotBlank
	@Size(max=20)
	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String name;

	@NotBlank
	@Size(max=20)
	@Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String lastName;

	@Pattern(regexp="\\d{10}", message = "only digits")
	@Size(max=10)
	@Column(name = "phone", nullable = true, columnDefinition = "VARCHAR(10)")
	private @Getter @Setter String phone;

	@Pattern(regexp="\\d{10}", message = "only digits")
	@Size(max=10)
	@Column(name = "cell_phone", nullable = true, columnDefinition = "VARCHAR(10)")
	private @Getter @Setter String cellPhone;

	@Size(max=20)
	@Column(name = "address", nullable = true, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String address;

	@Transient
	private @Setter String fullName;

	public String getFullName() {
		fullName = this.name + " " + this.lastName;
		return fullName;
	}

}
