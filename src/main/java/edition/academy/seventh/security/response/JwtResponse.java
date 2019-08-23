package edition.academy.seventh.security.response;

import edition.academy.seventh.security.AuthenticationService;
import edition.academy.seventh.security.request.LoginForm;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Wrapper containing accessToken and authorities defining privileges of user. Generated once user
 * attempts to login {@link AuthenticationService#login(LoginForm)}.
 *
 * @author Patryk Kucharski
 */
@EqualsAndHashCode
public class JwtResponse {
  private String accessToken;
  private String type = "Bearer";
  private String username;
  private Collection<? extends GrantedAuthority> authorities;

  /**
   * Creates JwtResponse with required properties and toked which will be held locally by the client
   * user. Contains {@link GrantedAuthority authorities} defining user privileges.
   *

   * @param accessToken Generated JSON web token JwtProvider.generateJwtToken(Authentication).
   * @param username of a user attempting to log in.
   * @param authorities user privileges.
   */
  public JwtResponse(
      String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
    this.accessToken = accessToken;
    this.username = username;
    this.authorities = authorities;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getType() {
    return type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
}
