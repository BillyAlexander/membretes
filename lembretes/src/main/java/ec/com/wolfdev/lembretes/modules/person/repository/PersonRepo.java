package ec.com.wolfdev.lembretes.modules.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.person.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
	public Person findByDocumentIdIgnoreCase(String documentId);
}
