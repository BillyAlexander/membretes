package ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class CatalogAdminModel extends RepresentationModel<CatalogAdminModel> {
	private Long id;
	private String name;
	private String code;
	private String other;
	private CatalogAdminModel parent;
	private List<CatalogAdminModel> children;
	private Integer level;
	private Boolean hasChildren;
}
