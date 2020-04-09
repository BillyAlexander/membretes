package ec.com.wolfdev.lembretes.modules.user.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.wolfdev.lembretes.modules.user.entity.User;
import ec.com.wolfdev.lembretes.modules.user.entity.lite.UserLite;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	public User findByUserNameIgnoreCaseAndIsDeletedFalseAndStatusTrue(String userName);
	
	public List<UserLite> findByIsDeletedFalseAndStatusTrue( Sort sort);
}
