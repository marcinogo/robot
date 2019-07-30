package edition.academy.seventh.security.service;

/**
 * Gets thrown when user's role wasn't found in database while trying to create new account.
 *
 * @author Patryk Kucharski
 */
public class RoleNotFoundException extends Exception {

  RoleNotFoundException(String message) {
    super(message);
  }
}
