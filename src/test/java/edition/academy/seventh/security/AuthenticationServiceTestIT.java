package edition.academy.seventh.security;

import edition.academy.seventh.security.request.LoginForm;
import edition.academy.seventh.security.request.RegisterForm;
import edition.academy.seventh.security.response.JwtResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class AuthenticationServiceTestIT {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void should_findUser_when_userIsRegistered() {
        //Given
        RegisterForm registerForm = new RegisterForm("admin2@admin.pl", "admin2", Set.of("ROLE_ADMIN"), "password");

        //When
        authenticationService.createNewAccount(registerForm);
        boolean isUserInDb = authenticationService.userExistsByUsername("admin2");

        //Then
        assertTrue(isUserInDb);
    }

    @Test
    public void should_notFindUser_when_userIsNotRegistered() {
        //Given

        //When
        boolean isUserInDb = authenticationService.userExistsByUsername("admin3");

        //Then
        assertFalse(isUserInDb);
    }


    @Test(expected = InternalAuthenticationServiceException.class)
    public void should_throwException_when_tryToLoginUserWhichIsNotRegistered() {
        //Given
        LoginForm loginForm = new LoginForm("admin4", "admin");

        //When
        JwtResponse login = authenticationService.login(loginForm);

        //Then
        //expect exception
    }

    @Test
    public void should_getJwtResponse_when_tryToLoginUserWhichIsRegistered() {
        //Given
        RegisterForm registerForm = new RegisterForm("admin4@admin.pl", "admin4", Set.of("ROLE_ADMIN"), "password");
        LoginForm loginForm = new LoginForm("admin4", "password");

        //When
        authenticationService.createNewAccount(registerForm);
        JwtResponse login = authenticationService.login(loginForm);

        //Then
        assertFalse(login.getToken().isEmpty());
    }


}
