package ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas.CatalogAdminModel;
import ec.com.wolfdev.lembretes.modules.role.hateoas.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class PermissionModuleModel extends RepresentationModel<PermissionModuleModel> {
	private Long id;
	private CatalogAdminModel module;
	private CatalogAdminModel permission;
	private RoleModel role;
}
