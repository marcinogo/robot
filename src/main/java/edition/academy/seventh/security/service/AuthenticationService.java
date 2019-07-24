package edition.academy.seventh.security.service;

import edition.academy.seventh.security.dao.RoleRepository;
import edition.academy.seventh.security.dao.UserRepository;
import edition.academy.seventh.security.jwt.JwtAuthEntryPoint;
import edition.academy.seventh.security.jwt.JwtProvider;
import edition.academy.seventh.security.model.Role;
import edition.academy.seventh.security.model.User;
import edition.academy.seventh.security.model.UserRole;
import edition.academy.seventh.security.model.request.LoginForm;
import edition.academy.seventh.security.model.request.RegisterForm;
import edition.academy.seventh.security.model.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides API for creating new account and logging in.
 * Also uses {@link UserRepository} while checking if given
 * user exists by passed param.
 *
 * @author Patryk Kucharski
 */
@Service
public class AuthenticationService {

  private AuthenticationManager authenticationManager;
  private JwtProvider jwtProvider;
  private PasswordEncoder encoder;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  /**
   * Creates bean for {@link org.springframework.context.ApplicationContext}.
   *
   * @param authenticationManager which processes {@link Authentication} request
   * @param jwtProvider generates tokens
   * @param encoder password encoder for safety.
   * @param userRepository speaks with database managing {@link User user} entities.
   * @param roleRepository speaks with database managing {@link Role role} entities.
   */
  @Autowired
  public AuthenticationService(
      AuthenticationManager authenticationManager,
      JwtProvider jwtProvider,
      PasswordEncoder encoder,
      UserRepository userRepository,
      RoleRepository roleRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
    this.encoder = encoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  /**
   * Creates new user's account based on {@link RegisterForm} passed as parameter.
   *
   * @param registerForm {@link RegisterForm} with requested user data needed to create an account.
   */
  public void createNewAccount(@RequestBody @Valid RegisterForm registerForm) {
    User user =
        new User(
            registerForm.getUsername(),
            registerForm.getEmail(),
            encoder.encode(registerForm.getPassword()));

    Set<String> rolesAsString = registerForm.getRole();
    Set<Role> roles = new HashSet<>();

    rolesAsString.forEach(
        role -> {
          if ("admin".equals(role)) {
            Role adminRole =
                roleRepository
                    .findByName(UserRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin Role not found."));
            roles.add(adminRole);
          } else if ("user".equals(role)) {
            Role userRole =
                roleRepository
                    .findByName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("User Role not found."));
            roles.add(userRole);
          }
        });

    user.setRoles(roles);
    userRepository.save(user);
    logger.info("created new account: " + user.toString());
  }

  /**
   * Tries to login user and if successful generates new JSON web token.
   *
   * @param loginForm {@link LoginForm} with email & password needed to be authorized
   * @return {@link JwtResponse} response with suitable JSON web token.
   */
  public JwtResponse login(@Valid @RequestBody LoginForm loginForm) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginForm.getUsername(), loginForm.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
  }

  public boolean userExistsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean userExistsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}