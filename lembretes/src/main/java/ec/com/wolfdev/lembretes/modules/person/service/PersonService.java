package ec.com.wolfdev.lembretes.modules.person.service;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.message.MessageControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.hateoas.PersonModel;
import ec.com.wolfdev.lembretes.modules.person.hateoas.PersonModelAssembler;
import ec.com.wolfdev.lembretes.modules.person.repository.PersonRepo;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.utils.AppMessage;
import ec.com.wolfdev.lembretes.utils.AppMethods;

@Service
public class PersonService extends BaseService<User> {

	public PersonService() {
		super(User.class);
	}

	@Autowired
	private PersonRepo personRepo;

	@Autowired
	private PersonModelAssembler personModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Person> pagedResourcesAssembler;

	@Transactional
	public ResponseEntity<?> getPeople() throws ServletException {
		try {
			return new ResponseEntity<List<Person>>(personRepo.findAll(), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getPeopleBySearch(String documentId, String name, String lastName, Boolean status,
			Boolean isDeleted, Date startDate, Date endDate, int page, int size, String fieldName, String direction) {
		try {
			return new ResponseEntity<PagedModel<PersonModel>>(pagedResourcesAssembler.toModel(
					personRepo.findPersonBySearch(documentId, name, lastName, status, isDeleted, startDate, endDate,
							PageRequest.of(page, size, AppMethods.sortFound(fieldName, direction))),
					personModelAssembler), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getPerson(Long id) {
		try {
			PersonModel personModel = personRepo.findById(id).map(personModelAssembler::toModel).orElse(null);
			if (personModel == null)
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else if (!personModel.getStatus() || personModel.getIsDeleted()) {
				return new ResponseEntity<ErrorControl>(new ErrorControl(
						String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, personModel.getDocumentId()),
						HttpStatus.NOT_FOUND.value(), true), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<PersonModel>(personModel, HttpStatus.OK);
			}
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> deletePerson(Long id) {
		try {
			return personRepo.findById(id).map(found -> {
				found.setIsDeleted(true);
				return personRepo.save(found);
			}).orElse(null) == null
					? new ResponseEntity<ErrorControl>(
							new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
							HttpStatus.NOT_FOUND)
					: new ResponseEntity<MessageControl>(
							new MessageControl(AppMessage.MSJ_DELETE_INFORMATION, HttpStatus.OK.value(), true),
							HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> savePerson(Person person, boolean isCreate) throws PgpException {
		try {

			if (isCreate) {
				Person found = personRepo.findByDocumentIdIgnoreCase(person.getDocumentId()).orElse(null);
				if (found != null) {
					return new ResponseEntity<ErrorControl>(
							new ErrorControl(AppMessage.MSJ_SIGNUP_DOCID_EXISTS + " " + person.getDocumentId(),
									HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			// save user
			String msg = "";
			if (isCreate) {
				msg = AppMessage.MSJ_CREATE_INFORMATION;
			} else {
				person = personReplace(person);
				if (person == null) {
					return new ResponseEntity<ErrorControl>(new ErrorControl(AppMessage.MSJ_UPDATE_WITHOUT_INFORMATION,
							HttpStatus.INTERNAL_SERVER_ERROR.value(), true), HttpStatus.INTERNAL_SERVER_ERROR);
				}
				if (person.getIsDeleted() || person.getStatus()) {
					return new ResponseEntity<ErrorControl>(new ErrorControl(
							String.format(AppMessage.MSJ_FOUND_INFORMATION_DELETED, person.getDocumentId()),
							HttpStatus.NOT_FOUND.value(), true), HttpStatus.NOT_FOUND);
				}
				msg = AppMessage.MSJ_UPDATE_INFORMATION;
			}

			personRepo.save(person);

			return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.OK.value(), true),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Person personReplace(Person rplPerson) {
		Person found = personRepo.findById(rplPerson.getId()).orElse(null);
		if (found != null) {
			found.setAddress(rplPerson.getAddress() == null ? found.getAddress() : rplPerson.getAddress());
			found.setCellPhone(rplPerson.getCellPhone() == null ? found.getCellPhone() : rplPerson.getCellPhone());
			found.setDocumentId(rplPerson.getDocumentId() == null ? found.getDocumentId() : rplPerson.getDocumentId());
			found.setLastName(rplPerson.getLastName() == null ? found.getLastName() : rplPerson.getLastName());
			found.setName(rplPerson.getName() == null ? found.getName() : rplPerson.getName());
			found.setPhone(rplPerson.getPhone() == null ? found.getPhone() : rplPerson.getPhone());
		}
		return found;
	}

}
