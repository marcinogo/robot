package edition.academy.seventh.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRetrieverSmokeTest {

  @Autowired private BookRetriever bookRetriever;

  @Test
  public void should_loadContext_when_executeThisMethod() throws Exception {
    assertThat(bookRetriever).isNotNull();
  }
}
