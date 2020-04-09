package ec.com.wolfdev.lembretes.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.user.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
