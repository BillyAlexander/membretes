package ec.com.wolfdev.lembretes.modules.role.entity;

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
import ec.com.wolfdev.lembretes.modules.permission.entity.Permission;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "role_permission")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RolePermission extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "role_perm_permission_fk"))
	private @Getter @Setter Permission permission;
	
	@JsonBackReference("rolePermission")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "role_perm_role_fk"))
	private @Getter @Setter Role role;
}
