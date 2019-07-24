package edition.academy.seventh.security.model;

/**
 * Roles assigned to user while during registration process.
 *
 * @author Patryk Kucharski
 */
public enum UserRole {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_USER("ROLE_USER");

  private String userRole;

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserRole() {
    return userRole;
  }
}
