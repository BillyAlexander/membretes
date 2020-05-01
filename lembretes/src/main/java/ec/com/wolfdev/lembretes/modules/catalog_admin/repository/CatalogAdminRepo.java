package ec.com.wolfdev.lembretes.modules.catalog_admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.CatalogAdmin;
import ec.com.wolfdev.lembretes.modules.catalog_admin.entity.lite.CatalogAdminLite;

@Repository
public interface CatalogAdminRepo extends JpaRepository<CatalogAdmin, Long> {
	public List<CatalogAdmin> findAllByOrderByName();

	@Query("SELECT a " + "FROM CatalogAdmin a " + "WHERE " + "(:status IS NULL OR a.status=:status)  "
			+ "AND a.parent.code=:parentCode ")
	public List<CatalogAdmin> findByParentCode(@Param("parentCode") String parentCode, @Param("status") Boolean status);

	public List<CatalogAdmin> findByParentCodeAndStatus(String parentCode, Boolean status);

	@Query("SELECT a.id AS id, " + "a.name AS name, " + "a.code AS code, " + "a.status AS status, "
			+ "a.other AS other, " + "a.creationDate AS creationDate, " + "a.parent AS ap, "
			+ "CASE WHEN COUNT(ac) > 0 THEN TRUE ELSE FALSE END AS hasChildren, "
			+ "CASE WHEN a.parent IS NULL THEN 0  END AS level " + "FROM CatalogAdmin a " + "LEFT JOIN a.parent ap "
			+ "  LEFT JOIN a.children ac " + "WHERE " + "(:status IS NULL OR a.status=:status)  "
			+ "AND a.parent.code=:parentCode " + "GROUP BY a, ap " + "ORDER BY a.name, a.code")
	public List<CatalogAdminLite> findByParentCodeOrderByName(@Param("parentCode") String parentCode,
			@Param("status") Boolean status);

	@Query("SELECT a " + "FROM CatalogAdmin a " + "WHERE " + "(:status IS NULL OR a.status=:status)  "
			+ "AND a.code=:code ")
	public CatalogAdmin findByCode(@Param("code") String code, @Param("status") Boolean status);

	@Query("SELECT a " + "FROM CatalogAdmin a " + "WHERE " + "(:status IS NULL OR a.status=:status)  "
			+ "AND a.parent.id=:parentCode ")
	public List<CatalogAdmin> findByParentId(@Param("parentCode") Long parentCode, @Param("status") Boolean status);

	@Query("SELECT a.id AS id, " + "a.name AS name, " + "a.code AS code, " + "a.other AS other, "
			+ "a.creationDate AS creationDate, " + "a.parent AS ap, " + "a.status AS status, "
			+ "CASE WHEN COUNT(ac) > 0 THEN TRUE ELSE FALSE END AS hasChildren, "
			+ "CASE WHEN a.parent IS NULL THEN 0  END AS level " + "FROM CatalogAdmin a" + "  LEFT JOIN a.parent ap "
			+ "  LEFT JOIN a.children ac" + " WHERE " + "UPPER(a.code) LIKE UPPER('%' || :code || '%') AND "
			+ "UPPER(a.name) LIKE UPPER('%' || :name || '%') AND " + "(:status IS NULL OR a.status=:status) AND "
			+ "DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate) AND  " + " a.parent IS NULL   "
			+ "GROUP BY a, ap " + "ORDER BY a.name, a.code")
	public List<CatalogAdminLite> findCatalogsAdmin(@Param("name") String name, @Param("code") String code,
			@Param("status") Boolean status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
