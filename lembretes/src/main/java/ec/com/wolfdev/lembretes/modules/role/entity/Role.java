package ec.com.wolfdev.lembretes.modules.role.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.modules.role_permission_module.entity.RolePermissionModule;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "role", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" }, name = "role_name_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Role extends BaseEntity {
	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String name;
	
	@Column(name = "description", nullable = false, columnDefinition = "VARCHAR(200)")
	private @Getter @Setter String description;
	
	@JsonManagedReference("rolePermissionMoule")
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private @Getter @Setter List<RolePermissionModule> rolePermissionModules = new ArrayList<>();
}
