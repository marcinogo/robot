package edition.academy.seventh.security.dao;

/**
 * Responsible for retrieving user from database and checking if given user exists while registering
 * a new account
 *
 * @author krzysztof.kramarz
 */
import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;

class UserRepositoryImpl implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

  private ConnectorProvider connectorProvider;
  private EntityManager entityManager;

   UserRepositoryImpl() {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
  }

  @Override
  public Optional<User> findByUsername(String username) throws NoResultException {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> from = query.from(User.class);
    Path<Object> name = from.get("name");
    query.select(from).where(criteriaBuilder.equal(name, username));
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

  private Optional<User> findByEmail(String email) throws NoResultException {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> from = query.from(User.class);
    Path<Object> name = from.get("email");
    query.select(from).where(criteriaBuilder.equal(name, email));
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

  @Override
  public Boolean existsByUsername(String username) {
    try {
      findByUsername(username);
    } catch (NoResultException e) {
      return false;
    }
    return true;
  }

  @Override
  public Boolean existsByEmail(String email) {

    try {
      findByEmail(email);
    } catch (NoResultException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean save(User user) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();
    entityManager.close();
    return false;
  }
}
