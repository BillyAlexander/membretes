package ec.com.wolfdev.lembretes.modules.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_name" }, name = "user_username_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity {
	@Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(60)")
	private @Getter @Setter String userName;
	
	@Column(name = "password", nullable = false, columnDefinition = "VARCHAR(200)")
	private @Getter @Setter String password;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "user_person_fk"), nullable = false)	
	private @Setter @Getter Person person;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role", foreignKey = @ForeignKey(name = "user_profile_fk"), nullable = false)
	private Role role;
}
