package edition.academy.seventh.security.model;

import lombok.Getter;

/**
 * Roles assigned to user during registration process.
 * Currently every registered user receives {@code ROLE_USER},
 * while {@code ROLE_ADMIN} will be given to specified users
 * once given version of the app runs for it's first time.
 *
 * Roles define privileges and accessibility to client & serer
 * resources
 *
 * @author Patryk Kucharski
 */
@Getter
public enum RoleName {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_USER("ROLE_USER");

  private String roleName;

  RoleName(String name) {
    this.roleName = name;
  }
}
