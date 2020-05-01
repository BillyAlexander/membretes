package ec.com.wolfdev.lembretes.modules.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.utils.AuthProvider;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_name" }, name = "user_username_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Setter
@Getter
public class User extends BaseEntity {
	@Email
	@NotBlank
	@Size(max = 60)
	@Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(60)")
	private String userName;

	@Size(max = 200)
	@Column(name = "password", nullable = true, columnDefinition = "VARCHAR(200)")
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	// information
	@NotBlank
	@Size(max = 20)
	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String name;

	@NotBlank
	@Size(max = 20)
	@Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String lastName;

	@Pattern(regexp = "\\d{10}", message = "only digits")
	@Size(max = 10)
	@Column(name = "phone", nullable = true, columnDefinition = "VARCHAR(10)")
	private @Getter @Setter String phone;

	@Pattern(regexp = "\\d{10}", message = "only digits")
	@Size(max = 10)
	@Column(name = "cell_phone", nullable = true, columnDefinition = "VARCHAR(10)")
	private @Getter @Setter String cellPhone;

	@Size(max = 20)
	@Column(name = "address", nullable = true, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String address;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "user_role_fk"), nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "usergroup_id", foreignKey = @ForeignKey(name = "usergroup_fk"))
	private CatalogAdmin userGroup;
}
