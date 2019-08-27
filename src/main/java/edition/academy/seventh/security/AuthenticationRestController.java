package edition.academy.seventh.security;

import edition.academy.seventh.security.request.LoginForm;
import edition.academy.seventh.security.request.RegisterForm;
import edition.academy.seventh.security.response.JwtResponse;
import edition.academy.seventh.security.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Main entry point for authentication and authorization purposes. Contains login and register
 * endpoints and proceeds with requested operations returning {@link ResponseEntity proper
 * response}.
 *
 * @author Patryk Kucharski
 */
@CrossOrigin("${robot.crossorigin}")
@RestController
@RequestMapping("/auth")
class AuthenticationRestController {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);
  private AuthenticationService authenticationService;

  @Autowired
  public AuthenticationRestController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/sign_in")
  ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
    JwtResponse jwtResponse = authenticationService.login(loginForm);
    logger.info("attempt to login " + loginForm.toString());
    return ResponseEntity.ok(jwtResponse);
  }

  @PostMapping("/sign_up")
  ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerForm) {
    registerForm.setRole(Set.of("user"));
    String returnMessage = "Couldn't register new account, something went wrong!";
    if (userWithThisUsernameAlreadyExists(registerForm)) {
      returnMessage = "This username is already taken! ";
      logger.error(returnMessage + registerForm.getUsername());
      return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.BAD_REQUEST);
    }
    if (userWithThisEmailAlreadyExists(registerForm)) {
      returnMessage = "Account with given email already exists! ";
      logger.error(returnMessage + registerForm.getEmail());
      return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.BAD_REQUEST);
    }
    if (authenticationService.createNewAccount(registerForm)) {
      returnMessage = "User registered successfully! ";
      logger.info(returnMessage + registerForm.toString());
      return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.OK);
  }

  private boolean userWithThisEmailAlreadyExists(@RequestBody @Valid RegisterForm registerForm) {
    return authenticationService.userExistsByEmail(registerForm.getEmail());
  }

  private boolean userWithThisUsernameAlreadyExists(@RequestBody @Valid RegisterForm registerForm) {
    return authenticationService.userExistsByUsername(registerForm.getUsername());
  }
}
