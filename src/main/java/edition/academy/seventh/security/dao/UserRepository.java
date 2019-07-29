package edition.academy.seventh.security.dao;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Optional;

import static edition.academy.seventh.database.connector.DatabaseTypes.H2;

/**
 * Responsible for retrieving user from database and checking if given user exists while registering
 * a new account.
 *
 * @author krzysztof.kramarz
 */
@Repository
public class UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

  private ConnectorProvider connectorProvider;
  private EntityManager entityManager;

  @Autowired
  public UserRepository() {
    connectorProvider = ConnectorFactory.of(H2);
  }

  /**
   * Retrieves {@link User} with specified {@link User#getUsername()}. Username is unique and can't
   * be duplicated, so method guarantees returning either single {@link User} or throwing {@link
   * NoResultException} when one wasn't found.
   *
   * @param username of searched user.
   * @return {@link User} which username was passed as parameter.
   * @throws NoResultException when user with given name wasn't found in database.
   */
  public Optional<User> findByUsername(String username) throws NoResultException {
    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
    Root<User> from = query.from(User.class);
    Path<Object> name = from.get("username");
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

  /**
   * Retrieves {@link User} with specified {@link User#getEmail()} ()}.
   * Email is unique and can't be duplicated, so method guarantees returning
   * either single {@link User} or throwing {@link NoResultException} when
   * one wasn't found.
   *
   * @param email of searched user.
   * @return {@link User} which username was passed as parameter.
   * @throws NoResultException when user with given email wasn't found in database.
   */
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

  /**
   * Saves given {@link User} to database.
   *
   * @param user to be saved
   * @return true if operation was successful or false
   *         if otherwise.
   */
  public boolean saveUser(User user) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user);
      transaction.commit();
    } catch (EntityExistsException
        | TransactionRequiredException
        | IllegalArgumentException
        | RollbackException
        | IllegalStateException e) {
      LOGGER.error("Persisting data failed. {}", e.getMessage());
      return false;
    } finally {
      entityManager.close();
    }
    return true;
  }

  /**
   * Checks if {@link User} with given username exists in database.
   *
   * @param username of user to be checked.
   * @return true if {@link User} with given username does exist in database
   *         or false otherwise.
   */
  public Boolean existsByUsername(String username) {
    try {
      findByUsername(username);
    } catch (NoResultException e) {
      return false;
    }
    return true;
  }

  /**
   * Checks if {@link User} with given email exists in database.
   *
   * @param email of user to be checked.
   * @return true if {@link User} with given email does exist in database
   *         or false otherwise.
   */
  public Boolean existsByEmail(String email) {
    try {
      findByEmail(email);
    } catch (NoResultException e) {
      return false;
    }
    return true;
  }
}
