package edition.academy.seventh.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationRestControllerSmokeTest {

  @Autowired private AuthenticationRestController authenticationRestController;

  @Test
  public void should_loadContext_when_executeThisMethod() throws Exception {
    assertThat(authenticationRestController).isNotNull();
  }
}
