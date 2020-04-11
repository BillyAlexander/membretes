package ec.com.wolfdev.lembretes.modules.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.entity.lite.UserLite;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	public User findByUserNameIgnoreCaseAndIsDeletedFalseAndStatusTrue(String userName);

	public List<UserLite> findByIsDeletedFalseAndStatusTrue(Sort sort);

	@Query("SELECT a" + " FROM User a WHERE" + " UPPER(a.userName) LIKE UPPER('%' || :userName || '%') AND"
			+ " (:personId IS NULL OR a.person.id=:personId) AND " + " (:roleId IS NULL OR a.role.id=:roleId) AND "
			+ " UPPER(a.person.name) LIKE UPPER('%' || :personName || '%') AND"
			+ " UPPER(a.person.lastName) LIKE UPPER('%' || :personLastName || '%') AND"
			+ " UPPER(a.person.documentId) LIKE UPPER('%' || :personDocumentId || '%') AND"
			+ " (:userGroupId IS NULL OR a.userGroup.id=:userGroupId) AND "
			+ " (:status IS NULL OR a.status=:status) AND" + " (:isDeleted IS NULL OR a.isDeleted=:isDeleted) AND"
			+ " DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)")
	public Page<UserLite> findUserBySearch(@Param("personId") Long personId, @Param("userName") String userName,
			@Param("personDocumentId") String personDocumentId, @Param("personName") String personName,
			@Param("personLastName") String personLastName, @Param("roleId") Long roleId,
			@Param("userGroupId") Long userGroupId, @Param("status") Boolean status,
			@Param("isDeleted") Boolean isDeleted, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			Pageable page);
}
