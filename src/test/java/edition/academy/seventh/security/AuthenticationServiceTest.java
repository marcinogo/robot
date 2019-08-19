package edition.academy.seventh.security;

import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.RoleName;
import edition.academy.seventh.security.model.User;
import edition.academy.seventh.security.dto.request.RegisterForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

/** @author Patryk Kucharski */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
  @Mock UserRepository userRepository;
  @Mock RoleRepository roleRepository;
  @Mock PasswordEncoder encoder;

  @InjectMocks private AuthenticationService authenticationService;

  @Test
  public void testCreateNewAccount() {
    Role role = new Role();
    role.setId(1L);
    role.setName(RoleName.ROLE_USER);
    when(this.encoder.encode("test")).thenReturn("encoded");
    when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
    User user = new User("test@test.com", "test", encoder.encode("test"), Set.of(role));

    when(userRepository.saveUser(eq(user))).thenReturn(true);
    authenticationService.createNewAccount(
        new RegisterForm("test@test.com", "test", Set.of("user"), "test"));
    verify(userRepository, times(1)).saveUser(user);
  }
}
