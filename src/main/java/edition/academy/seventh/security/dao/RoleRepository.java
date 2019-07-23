package edition.academy.seventh.security.dao;

import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(UserRole roleName);
}
