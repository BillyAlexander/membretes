package ec.com.wolfdev.lembretes.modules.permission.entity;

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
@Table(schema = Const.SCHEMA, name = "permission", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "action" }, name = "permission_action_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Permission extends BaseEntity {
	@Column(name = "action", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String action;
	
	@Column(name = "description", nullable = false, columnDefinition = "VARCHAR(200)")
	private @Getter @Setter String description;

}
