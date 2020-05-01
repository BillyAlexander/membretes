package ec.com.wolfdev.lembretes.modules.role_permission_module.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.role.entity.Role;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "role_permission_module")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RolePermissionModule extends BaseEntity {

	@JsonBackReference("rolePermissionMoule")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "role_perm_role_fk"))
	private @Getter @Setter Role role;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "perm_mod_permission_fk"))
	private @Getter @Setter CatalogAdmin permission;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "perm_mod_module_fk"))
	private @Getter @Setter CatalogAdmin module;

}
