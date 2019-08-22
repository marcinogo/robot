package edition.academy.seventh.security;

import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.RoleName;
import edition.academy.seventh.security.model.User;
import edition.academy.seventh.security.request.RegisterForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
  @Mock UserRepository userRepository;
  @Mock RoleRepository roleRepository;
  @Mock PasswordEncoder encoder;

  @InjectMocks private AuthenticationService authenticationService;

  @Test
  public void should_createNewAccount() {
    // Given
    Role role = new Role(RoleName.ROLE_USER);
    role.setId(1L);

    // When
    when(this.encoder.encode("test")).thenReturn("encoded");
    when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
    User user = new User("test@test.com", "test", encoder.encode("test"), Set.of(role));

    when(userRepository.saveUser(eq(user))).thenReturn(true);
    authenticationService.createNewAccount(
        new RegisterForm("test@test.com", "test", Set.of("user"), "test"));

    // Then
    verify(userRepository, times(1)).saveUser(user);
  }
}
