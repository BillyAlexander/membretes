package ec.com.wolfdev.lembretes.modules.catalog_admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas.CatalogAdminModel;
import ec.com.wolfdev.lembretes.modules.catalog_admin.hateoas.CatalogAdminModelAssembler;
import ec.com.wolfdev.lembretes.modules.catalog_admin.repository.CatalogAdminRepo;
import ec.com.wolfdev.lembretes.utils.AppMessage;

@Service
public class CatalogAdminService extends BaseService<CatalogAdmin> {

	public CatalogAdminService() {
		super(CatalogAdmin.class);
	}

	@Autowired
	private CatalogAdminRepo catalogAdminRepo;

	@Autowired
	private CatalogAdminModelAssembler catalogAdminModelAssembler;

//	@Autowired
//	private PagedResourcesAssembler<CatalogAdmin> pagedResourcesAssembler;

	public ResponseEntity<?> getCatalogAdmin(Long id) {
		try {
			CatalogAdmin catalog = catalogAdminRepo.findById(id).orElse(null);
			if (catalog == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}
			CatalogAdminModel catalogModel = catalogAdminModelAssembler.toModel(catalog);

			return new ResponseEntity<CatalogAdminModel>(catalogModel, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
