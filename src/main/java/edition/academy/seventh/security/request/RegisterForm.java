package edition.academy.seventh.security.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Represents register form on the client side.
 *
 * @author Patryk Kucharski
 */
@ToString
@Getter
@NoArgsConstructor
public class RegisterForm {

  @NotBlank
  @Size(max = 60)
  @Email
  private String email;

  @NotBlank
  @Size(min = 3, max = 50)
  private String username;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  public RegisterForm(
      @NotBlank @Size(max = 60) @Email String email,
      @NotBlank @Size(min = 3, max = 50) String username,
      Set<String> role,
      @NotBlank @Size(min = 6, max = 40) String password) {
    this.email = email;
    this.username = username;
    this.role = role;
    this.password = password;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
