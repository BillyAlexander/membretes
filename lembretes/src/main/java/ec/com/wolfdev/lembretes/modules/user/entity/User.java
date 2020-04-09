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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.modules.user_group.entity.UserGroup;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_name" }, name = "user_username_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity {
	@Email @NotBlank
	@Size(max=60)
	@Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(60)")
	private @Getter @Setter String userName;
	
	@NotBlank
	@Size(max=200)
	@Column(name = "password", nullable = false, columnDefinition = "VARCHAR(200)")
	private @Getter @Setter String password;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "user_person_fk"), nullable = false)	
	private @Setter @Getter Person person;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "user_role_fk"), nullable = false)
	private @Setter @Getter Role role;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "usergroup_id", foreignKey = @ForeignKey(name = "usergroup_role_fk"), nullable = false)
	private @Setter @Getter UserGroup userGroup;
}
