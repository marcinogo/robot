package edition.academy.seventh.security.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents user in terms of persistence.
 *
 * @author Patryk Kucharski
 */
@Data
@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NaturalId
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(min = 3, max = 50)
  private String username;

  @NotBlank
  @Size(min = 6, max = 100)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(
      @NotBlank @Size(max = 50) @Email String email,
      @NotBlank @Size(min = 3, max = 50) String username,
      @NotBlank @Size(min = 6, max = 100) String password,
      Set<Role> roles) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public User(
      @NotBlank @Size(max = 50) String email,
      @NotBlank @Size(min = 3, max = 50) String username,
      @NotBlank @Size(min = 6, max = 100) String password) {
    this.email = email;
    this.username = username;
    this.password = password;
  }

  public User(
      @NotBlank @Size(max = 50) String email, @NotBlank @Size(min = 6, max = 100) String password) {
    this.email = email;
    this.password = password;
  }

  public User() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(email, user.email)
        && Objects.equals(username, user.username)
        && Objects.equals(password, user.password)
        && Objects.equals(roles, user.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, username, password, roles);
  }
}
