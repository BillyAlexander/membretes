package ec.com.wolfdev.lembretes.modules.role_permission_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.role_permission_module.entity.RolePermissionModule;

@Repository
public interface PermissionModuleRepo extends JpaRepository<RolePermissionModule, Long> {

}
