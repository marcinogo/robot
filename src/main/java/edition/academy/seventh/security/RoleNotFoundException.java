package edition.academy.seventh.security;

/**
 * Gets thrown when user's role wasn't found in database while trying to create new account.
 *
 * @author Patryk Kucharski
 */
class RoleNotFoundException extends Exception {

  RoleNotFoundException(String message) {
    super(message);
  }
}
