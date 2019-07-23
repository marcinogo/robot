package edition.academy.seventh.security.dao;

import edition.academy.seventh.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Responsible for retrieving user from database and checking if given user exists while
 * registering a new account
 *
 * @author Patryk Kucharski
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
