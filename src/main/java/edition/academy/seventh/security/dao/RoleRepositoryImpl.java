package edition.academy.seventh.security.dao;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
  public Optional<Role> findByName(UserRole roleName) throws NoResultException {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
    Root<Role> from = query.from(Role.class);
    Path<Object> name = from.get("name");
    query.select(from).where(criteriaBuilder.equal(name, roleName.getUserRole()));
    try {
      return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    } catch (Exception e) {
      String message = e.getMessage();
      LOGGER.error("Retrieving data from db unsuccessful. {}", message);
      throw new NoResultException(
          String.format("Retrieving data from db unsuccessful. Message: %s", message));
    } finally {
      entityManager.close();
    }
  }
}
