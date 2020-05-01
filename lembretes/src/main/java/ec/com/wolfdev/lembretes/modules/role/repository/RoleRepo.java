package ec.com.wolfdev.lembretes.modules.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.role.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

}
