package ec.com.wolfdev.lembretes.modules.person.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.ServletException;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import ec.com.wolfdev.lembretes.modules.person.controller.PersonController;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.utils.Const;

@Component
public class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {

	public PersonModelAssembler() {
		super(PersonController.class, PersonModel.class);
	}

	@Override
	public PersonModel toModel(Person entity) {
		PersonModel personModel = instantiateModel(entity);
		try {
			personModel.add(linkTo(methodOn(PersonController.class).getPerson(entity.getId())).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		personModel.setId(entity.getId());
		personModel.setAddress(entity.getAddress());
		personModel.setCellPhone(entity.getCellPhone());
		personModel.setCreationDate(entity.getCreationDate());
		personModel.setDocumentId(entity.getDocumentId());
		personModel.setIsDeleted(entity.getIsDeleted());
		personModel.setLastName(entity.getLastName());
		personModel.setName(entity.getName());
		personModel.setPhone(entity.getPhone());
		personModel.setStatus(entity.getStatus());

		return personModel;
	}

	@Override
	public CollectionModel<PersonModel> toCollectionModel(Iterable<? extends Person> entities) {

		CollectionModel<PersonModel> peopleModel = super.toCollectionModel(entities);
		try {
			peopleModel.add(linkTo(methodOn(PersonController.class).getPeople(Const.PAGE_0_DEFAULT, Const.PAGE_SIZE_DEFAULT, Const.SORT_FIELD_DEFAULT, Const.SORT_DESC)).withSelfRel());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		return peopleModel;
	}
	

}
