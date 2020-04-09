package ec.com.wolfdev.lembretes.modules.person.controller;

import javax.servlet.ServletException;

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
import org.springframework.web.bind.annotation.RestController;

import ec.com.wolfdev.lembretes.core.base.exception.PgpException;
import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.service.PersonService;
import ec.com.wolfdev.lembretes.utils.Const;

@RestController
@RequestMapping(value = Const.API_PRIVATE+"person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<?> getPeople() throws ServletException {
		return personService.getPeople();
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getPerson(@PathVariable("id") Long id) throws ServletException {
		return personService.getPerson(id);
	}
	
	@PostMapping
	public ResponseEntity<?> createPerson(@RequestBody Person person) throws ServletException, PgpException {
		return personService.savePerson(person, true);
	}
	
	@PutMapping(path = "{id}")
	public ResponseEntity<?> updatePerson(@PathVariable("id") Long id,@RequestBody Person person) throws ServletException, PgpException {
		person.setId(id);
		return personService.savePerson(person,false);
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deletePerson(@PathVariable("id") Long id ) throws ServletException {
		return personService.deletePerson(id);
	}
}
