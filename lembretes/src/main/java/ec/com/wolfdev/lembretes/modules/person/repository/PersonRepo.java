package ec.com.wolfdev.lembretes.modules.person.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.person.entity.Person;
import ec.com.wolfdev.lembretes.modules.person.entity.Lite.PersonLite;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
	public Person findByDocumentIdIgnoreCaseAndIsDeletedFalseAndStatusTrue(String documentId);

	public List<PersonLite> findByIsDeletedFalseAndStatusTrue();

	@Query("SELECT a" + " FROM Person a WHERE" + " UPPER(a.name) LIKE UPPER('%' || :name || '%') AND"
			+ " UPPER(a.lastName) LIKE UPPER('%' || :lastName || '%') AND"
			+ " (a.documentId) LIKE '%' || :documentId || '%' AND" + " (:status IS NULL OR a.status=:status) AND"
			+ " (:isDeleted IS NULL OR a.isDeleted=:isDeleted) AND"
			+ " DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)")
	public List<PersonLite> findPersonBySearch(@Param("documentId") String documentId, @Param("name") String name,
			@Param("lastName") String lastName, @Param("status") Boolean status, @Param("isDeleted") Boolean isDeleted,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable page);
}
