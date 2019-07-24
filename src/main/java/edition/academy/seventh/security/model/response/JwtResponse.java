package edition.academy.seventh.security.model.response;

import edition.academy.seventh.security.model.request.LoginForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Wrapper containing token and authorities defining privileges
 * of user. Generated once user attempts to login
 * {@link edition.academy.seventh.security.service.AuthenticationService#login(LoginForm)}.
 *
 * @author Patryk Kucharski
 */
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String username;
  private Collection<? extends GrantedAuthority> authorities;

  /**
   * Creates JwtResponse with required properties and toked which will
   * be held by locally by client user. Contains {@link GrantedAuthority authorities}
   * defining user privileges.
   *
   * @param accessToken Generated JSON web token
   *        {@link edition.academy.seventh.security.jwt.JwtProvider#generateJwtToken(Authentication)}.
   * @param username of user attempting to log in.
   * @param authorities user privileges.
   */
  public JwtResponse(
      String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
    this.token = accessToken;
    this.username = username;
    this.authorities = authorities;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
