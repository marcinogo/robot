package edition.academy.seventh.security.dao;

import edition.academy.seventh.security.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Responsible for retrieving user from database and checking if
 * given user exists while registering a new account.
 *
 * @author Patryk Kucharski
 */
@Repository
public interface UserRepository {

  Optional<User> findByUsername(String username) throws NoResultException;

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  boolean saveUser(User user);
}
