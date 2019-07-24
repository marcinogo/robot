package edition.academy.seventh.security.dao;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;
/**
 * Responsible for retrieving {@link Role roles} from database.
 *
 * @author krzysztof.kramarz
 */
class RoleRepositoryImpl implements RoleRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleRepositoryImpl.class);
  private ConnectorProvider connectorProvider;
  private EntityManager entityManager;

  RoleRepositoryImpl() {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
  }

  @Override
  public Optional<Role> findByName(UserRole roleName) {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
    Root<Role> from = query.from(Role.class);
    Path<Object> name = from.get("name");
    query.select(from).where(criteriaBuilder.equal(name, roleName.getUserRole()));
    Role role = entityManager.createQuery(query).getSingleResult();

    entityManager.close();
    return Optional.ofNullable(role);
  }
}
