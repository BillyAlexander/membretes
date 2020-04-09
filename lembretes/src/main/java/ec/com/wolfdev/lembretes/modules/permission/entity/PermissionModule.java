package ec.com.wolfdev.lembretes.modules.permission.entity;
import ec.com.wolfdev.lembretes.modules.module.entity.Module;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.wolfdev.lembretes.core.base.entity.BaseEntity;
import ec.com.wolfdev.lembretes.utils.Const;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = Const.SCHEMA, name = "permission_module")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PermissionModule extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "permission_id", foreignKey = @ForeignKey(name = "perm_mod_permission_fk"))
	private @Getter @Setter Permission permission;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "perm_mod_module_fk"))
	private @Getter @Setter Module module;
}
