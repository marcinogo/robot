package edition.academy.seventh.pagination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class PaginationControllerSmokeTestIT {

  @Autowired private PaginationController paginationController;

  @Test
  public void should_loadContext_when_executeThisMethod() throws Exception {
    assertThat(paginationController).isNotNull();
  }
}
