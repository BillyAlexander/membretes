package ec.com.wolfdev.lembretes.modules.person.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.service.PersonService;
import ec.com.wolfdev.lembretes.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE + "person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(value = "search")
	public ResponseEntity<?> getPeopleFind(@RequestParam(required = false, defaultValue = "") String documentId,
			@RequestParam(required = false, name = "name", defaultValue = "") String name,
			@RequestParam(required = false, name = "lastName", defaultValue = "") String lastName,
			@RequestParam(required = false, name = "status") Boolean status,
			@RequestParam(required = false, name = "isDeleted") Boolean isDeleted,
			@RequestParam(required = false, name = "startDate") Date startDate,
			@RequestParam(required = false, name = "endDate") Date endDate,
			@RequestParam(required = false, name = "page", defaultValue = "0") Integer page,
			@RequestParam(required = false, name = "size", defaultValue = "10") Integer size,
			@RequestParam(required = false, name = "fieldName", defaultValue = Const.SORT_FIELD_DEFAULT) String fieldName,
			@RequestParam(required = false, name = "direction", defaultValue = Const.SORT_DESC) String direction)
			throws ServletException {

		return personService.getPeopleBySearch(documentId, name, lastName, status, isDeleted,
				startDate == null ? new Date(0) : startDate, endDate == null ? new Date() : endDate, page, size,
				fieldName, direction);

	}

	@GetMapping
	public ResponseEntity<?> getPeople(@RequestParam(required = false, name = "page", defaultValue = "0") Integer page,
			@RequestParam(required = false, name = "size", defaultValue = "10") Integer size,
			@RequestParam(required = false, name = "fieldName", defaultValue = Const.SORT_FIELD_DEFAULT) String fieldName,
			@RequestParam(required = false, name = "direction", defaultValue = Const.SORT_DESC) String direction)
			throws ServletException {

		return personService.getPeopleBySearch("", "", "", null, null, new Date(0), new Date(), page, size, fieldName,
				direction);

	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> getPerson(@PathVariable(required = false, name = "id") Long id) throws ServletException {
		return personService.getPerson(id);
	}

	@PostMapping
	public ResponseEntity<?> createPerson(@RequestBody @Valid Person person) throws ServletException, PgpException {
		return personService.savePerson(person, true);
	}

	@PutMapping(path = "{id}")
	public ResponseEntity<?> updatePerson(@PathVariable(required = true, name = "id") Long id,
			@RequestBody Person person) throws ServletException, PgpException {
		person.setId(id);
		return personService.savePerson(person, false);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(required = true, name = "id") Long id) throws ServletException {
		return personService.deletePerson(id);
	}
}
