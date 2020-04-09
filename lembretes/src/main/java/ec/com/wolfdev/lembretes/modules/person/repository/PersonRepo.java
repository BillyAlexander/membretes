package ec.com.wolfdev.lembretes.modules.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.entity.Lite.PersonLite;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
	public Person findByDocumentIdIgnoreCaseAndIsDeletedFalseAndStatusTrue(String documentId);
	
	public List<PersonLite> findByIsDeletedFalseAndStatusTrue();
}
