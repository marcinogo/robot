package edition.academy.seventh.security;

import edition.academy.seventh.connector.ConnectorFactory;
import edition.academy.seventh.connector.ConnectorProvider;
import edition.academy.seventh.connector.DatabaseType;
import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * Responsible for retrieving {@link Role roles} from database.
 *
 * @author krzysztof.kramarz
 */
@Repository
class RoleRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleRepository.class);
  private ConnectorProvider connectorProvider;
  private EntityManager entityManager;

  RoleRepository(@Value("${robot.db}") String database) {
    connectorProvider = ConnectorFactory.of(DatabaseType.valueOf(database));
  }

  /**
   * Retrieves {@link Role} with specified {@link RoleName#name()}. Role is unique and can't be
   * duplicated, so method guarantees returning either single {@link Role} or throwing {@link
   * NoResultException} when one wasn't found.
   *
   * @param roleName of given {@link Role}
   * @return {@link Role} which name was passed as parameter.
   * @throws NoResultException when Role with given name wasn't found in database.
   */
  Optional<Role> findByName(RoleName roleName) throws NoResultException {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
    Root<Role> from = query.from(Role.class);
    Path<Object> name = from.get("name");
    query.select(from).where(criteriaBuilder.equal(name, roleName));
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

  void addRole(RoleName roleName) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(new Role(roleName));
    transaction.commit();
  }
}
