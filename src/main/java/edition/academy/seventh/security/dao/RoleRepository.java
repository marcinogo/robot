package edition.academy.seventh.security.dao;

import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Responsible for retrieving {@link Role roles} from database.
 *
 * @author Patryk Kucharski
 */
@Repository
public interface RoleRepository {

  Optional<Role> findByName(RoleName roleName);
}
