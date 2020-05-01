package ec.com.wolfdev.lembretes.modules.role.hateoas;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.com.wolfdev.lembretes.modules.role_permission_module.hateoas.PermissionModuleModel;
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
public class RoleModel extends RepresentationModel<RoleModel> {
	private Long id;
	private String name;
	private String description;
	private List<PermissionModuleModel> permissionModules;
}
