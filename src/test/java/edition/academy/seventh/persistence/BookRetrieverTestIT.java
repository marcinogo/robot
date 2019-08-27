package edition.academy.seventh.persistence;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class BookRetrieverTestIT {

  @Autowired private MockMvc mockMvc;

  @Test
  public void should_returnOkStatus_when_tryToRetrieveBooksWithAllowedOrigin() throws Exception {
    // Given

    // When
    this.mockMvc
        .perform(get("/books").header("Origin", "http://localhost:4200"))
        /*//Then*/ .andExpect(status().isOk());
  }

  @Test
  public void should_returnForbiddenStatus_when_tryToRetrieveBooksWithNotAllowedOrigin()
      throws Exception {
    // Given

    // When
    this.mockMvc
        .perform(get("/books").header("Origin", "http://localhost:4280"))
        /*//Then*/ .andExpect(status().isForbidden());
  }
}
