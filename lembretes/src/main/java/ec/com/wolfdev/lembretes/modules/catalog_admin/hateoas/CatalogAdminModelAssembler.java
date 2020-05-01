package ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.catalog_admin.controller.CatalogAdminController;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;

@Component
public class CatalogAdminModelAssembler extends RepresentationModelAssemblerSupport<CatalogAdmin, CatalogAdminModel> {

	public CatalogAdminModelAssembler() {
		super(CatalogAdminController.class, CatalogAdminModel.class);
	}

	@Override
	public CatalogAdminModel toModel(CatalogAdmin entity) {
		CatalogAdminModel catalogModel = instantiateModel(entity);
		catalogModel.setId(entity.getId());
		catalogModel.setCode(entity.getCode());
		catalogModel.setHasChildren(entity.getHasChildren());
		catalogModel.setLevel(entity.getLevel());
		catalogModel.setName(entity.getName());
		catalogModel.setOther(entity.getOther());
		if (catalogModel.getHasChildren())
			catalogModel.setChildren(this.ToChildren(entity.getChildren()));
//		if (entity.getParent() != null)
//			catalogModel.setParent(this.toModel(entity.getParent())); comment because throwing a update

		try {
			catalogModel.add(linkTo(methodOn(CatalogAdminController.class).getCatalog(entity.getId())).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return catalogModel;
	}

	@Override
	public CollectionModel<CatalogAdminModel> toCollectionModel(Iterable<? extends CatalogAdmin> entities) {

		CollectionModel<CatalogAdminModel> usersModel = super.toCollectionModel(entities);

		return usersModel;
	}

	public List<CatalogAdminModel> ToChildren(List<CatalogAdmin> entities) {

		List<CatalogAdminModel> result = new ArrayList<>();

		for (CatalogAdmin entity : entities) {
			result.add(this.toModel(entity));
		}
		return result;
	}

}
