package edition.academy.seventh.security.controller;

import edition.academy.seventh.security.model.request.LoginForm;
import edition.academy.seventh.security.model.request.RegisterForm;
import edition.academy.seventh.security.model.response.JwtResponse;
import edition.academy.seventh.security.model.response.ResponseMessage;
import edition.academy.seventh.security.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Main entry point for authentication and authorization purposes.
 * Contains login and register endpoints and proceeds with requested
 * operations returning {@link ResponseEntity proper response}.
 *
 * @author Patryk Kucharski
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

  private AuthenticationService authenticationService;
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);

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
    String returnMessage = "Couldn't register new account, smething went wrong!";
    if (authenticationService.userExistsByUsername(registerForm.getUsername())) {
      returnMessage = "This username is already taken! ";
      logger.error(returnMessage + registerForm.getUsername());
      return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.BAD_REQUEST);
    }
    if (authenticationService.userExistsByEmail(registerForm.getEmail())) {
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
}
