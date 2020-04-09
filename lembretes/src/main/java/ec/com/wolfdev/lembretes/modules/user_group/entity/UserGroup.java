package ec.com.wolfdev.lembretes.modules.user_group.entity;

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
@Table(schema = Const.SCHEMA, name = "user_group", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "name" }, name = "usergroup_name_uk") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserGroup extends BaseEntity {
	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(20)")
	private @Getter @Setter String name;
	
	@Column(name = "description", nullable = false, columnDefinition = "VARCHAR(200)")
	private @Getter @Setter String description;
}
