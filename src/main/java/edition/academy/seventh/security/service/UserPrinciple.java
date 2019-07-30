package edition.academy.seventh.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edition.academy.seventh.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class which is a representation of authenticated User {@link User} with granted authority.
 * UserPrinciple is returned after successful authentication in {@link UserDetailsServiceImpl}.
 *
 * @author Patryk Kucharski
 */
@Getter
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String email;
  @JsonIgnore private String password;
  private Collection<? extends GrantedAuthority> authorities;

  /**
   * Creates new {@code UserPrinciple} instance based on given user data.
   *
   * @param user {@link User} with required data.
   * @return UserPrinciple based on User passed as parameter.
   */
  static UserPrinciple build(User user) {
    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());

    return new UserPrinciple(
        user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserPrinciple user = (UserPrinciple) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, authorities);
  }
}
