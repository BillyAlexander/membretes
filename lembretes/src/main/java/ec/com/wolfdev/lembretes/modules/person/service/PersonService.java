package ec.com.wolfdev.lembretes.modules.person.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.wolfdev.lembretes.core.base.error.ErrorControl;
import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.core.base.message.MessageControl;
import ec.com.wolfdev.lembretes.core.base.service.BaseService;
import ec.com.wolfdev.lembretes.modules.person.controller.PersonSearch;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.entity.Lite.PersonLite;
import ec.com.wolfdev.lembretes.modules.person.repository.PersonRepo;
import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.utils.AppMessage;

@Service
public class PersonService extends BaseService<User> {

	public PersonService() {
		super(User.class);
	}

	@Autowired
	private PersonRepo personRepo;

	@Transactional
	public ResponseEntity<?> getPeople() {
		try {
			return new ResponseEntity<List<PersonLite>>(personRepo.findByIsDeletedFalseAndStatusTrue(), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getPeopleBySearch(PersonSearch search) {
		try {
			return new ResponseEntity<List<PersonLite>>(
					personRepo.findPersonBySearch(search.getDocumentId(), search.getName(), search.getLastName(),
							search.getStatus(), search.isDeleted, search.getStartDate(), search.getEndDate(), search.getPaginate().getPaginate()),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> getPerson(Long id) {
		try {
			Person found = personRepo.findById(id).orElse(null);
			if (found == null)
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else if (found.getIsDeleted())
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			else
				return new ResponseEntity<Person>(found, HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public ResponseEntity<?> deletePerson(Long id) {
		try {
			Person found = personRepo.findById(id).orElse(null);
			if (found == null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_NOT_FOUND_INFORMATION, HttpStatus.NOT_FOUND.value(), true),
						HttpStatus.NOT_FOUND);
			}
			found.setIsDeleted(true);
			personRepo.save(found);
			return new ResponseEntity<MessageControl>(
					new MessageControl(AppMessage.MSJ_DELETE_INFORMATION, HttpStatus.OK.value(), true), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> savePerson(Person person, boolean isCreate) throws PgpException {
		// String randomPassword="";
		if (isCreate) {
			Person found = personRepo.findByDocumentIdIgnoreCaseAndIsDeletedFalseAndStatusTrue(person.getDocumentId());
			if (found != null) {
				return new ResponseEntity<ErrorControl>(
						new ErrorControl(AppMessage.MSJ_SIGNUP_DOCID_EXISTS + " " + person.getDocumentId(),
								HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		// save user
		try {
			String msg = "";
			if (isCreate) {
				msg = AppMessage.MSJ_CREATE_INFORMATION;
			} else {
				person = personReplace(person);
				if (person == null) {
					msg = AppMessage.MSJ_UPDATE_WITHOUT_INFORMATION;
					return new ResponseEntity<ErrorControl>(
							new ErrorControl(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
				msg = AppMessage.MSJ_UPDATE_INFORMATION;
			}
			personRepo.save(person);

			return new ResponseEntity<MessageControl>(new MessageControl(msg, HttpStatus.OK.value(), true),
					HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<ErrorControl>(
					new ErrorControl(err.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), true),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public Person personReplace(Person rplPerson) {
		Person found = personRepo.findById(rplPerson.getId()).orElse(null);
		if (found != null) {
			found.setAddress(rplPerson.getAddress() == null ? found.getAddress() : rplPerson.getAddress());
			found.setCellPhone(rplPerson.getCellPhone() == null ? found.getCellPhone() : rplPerson.getCellPhone());
			found.setCellPhone(rplPerson.getCellPhone() == null ? found.getCellPhone() : rplPerson.getCellPhone());
			found.setDocumentId(rplPerson.getDocumentId() == null ? found.getDocumentId() : rplPerson.getDocumentId());
			found.setLastName(rplPerson.getLastName() == null ? found.getLastName() : rplPerson.getLastName());
			found.setName(rplPerson.getName() == null ? found.getName() : rplPerson.getName());
			found.setPhone(rplPerson.getPhone() == null ? found.getPhone() : rplPerson.getPhone());
			found.setIsDeleted(rplPerson.getIsDeleted() == null ? found.getIsDeleted() : rplPerson.getIsDeleted());
		}

		return found;
	}

}
