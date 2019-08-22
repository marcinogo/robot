package edition.academy.seventh.security.model;

import lombok.Getter;

/**
 * Roles assigned to user during registration process. Currently every registered user receives
 * {@code USER}, while {@code ADMIN} will be given to specified users once given version
 * of the app runs for it's first time. Roles define privileges and accessibility to server
 * resources and APIs.
 *
 * @author Patryk Kucharski
 */
@Getter
public enum RoleName {
  ADMIN("ADMIN"),
  USER("USER");

  private String roleName;

  RoleName(String name) {
    this.roleName = name;
  }
}
