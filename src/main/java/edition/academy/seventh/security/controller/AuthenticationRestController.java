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
    if (authenticationService.userExistsByUsername(registerForm.getUsername())) {
      String returnMessage = "This username is already taken! ";
      logger.error(returnMessage + registerForm.getUsername());
      return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.BAD_REQUEST);
    }
    if (authenticationService.userExistsByEmail(registerForm.getEmail())) {
      String returnMassage = "Account with given email already exists! ";
      logger.error(returnMassage + registerForm.getEmail());
      return new ResponseEntity<>(new ResponseMessage(returnMassage), HttpStatus.BAD_REQUEST);
    }
    authenticationService.createNewAccount(registerForm);
    String returnMessage = "User registered successfully! ";
    logger.info(returnMessage + registerForm.toString());
    return new ResponseEntity<>(new ResponseMessage(returnMessage), HttpStatus.OK);
  }
}
