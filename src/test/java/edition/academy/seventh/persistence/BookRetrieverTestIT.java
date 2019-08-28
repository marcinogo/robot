package edition.academy.seventh.persistence;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class BookRetrieverTestIT {

  @Autowired private MockMvc mockMvc;
  @MockBean private BookService bookService;
  private String token;

  @Before
  public void setup() throws Exception {
    token = getTokenForAuthorization();
  }

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

  @Test
  public void should_returnOkStatus_when_tryToRetrieveBookByIdWithAllowedOrigin() throws Exception {
    // Given
    when(bookService.getBookstoreBookDtoById(0l)).thenReturn(null);

    // When
    String token = getTokenForAuthorization();
    this.mockMvc
        .perform(
            get("/book/0")
                .header("Authorization", "Bearer " + token)
                .header("Origin", "http://localhost:4200"))
        /*//Then*/ .andExpect(status().isOk());
  }

  @Test
  public void should_returnForbiddenStatus_when_tryToRetrieveBookByIdWithNotAllowedOrigin()
      throws Exception {
    // Given
    when(bookService.getBookstoreBookDtoById(0l)).thenReturn(null);

    // When
    this.mockMvc
        .perform(
            get("/book/0")
                .header("Authorization", "Bearer " + token)
                .header("Origin", "http://localhost:4280"))
        /*//Then*/ .andExpect(status().isForbidden());
  }

  private String getTokenForAuthorization() throws Exception {
    this.mockMvc.perform(
        post("/auth/sign_up")
            .content(
                "{\n"
                    + "\t\"email\": \"fajnyuser@o2.pl\",\n"
                    + "\t\"username\": \"fajnyuser\",\n"
                    + "\t\"role\": [\"user\"],\n"
                    + "\t\"password\": \"spoko12\"\n"
                    + "}")
            .contentType("application/json"));

    String body =
        this.mockMvc
            .perform(
                post("/auth/sign_in")
                    .content(
                        "{\n"
                            + "\t\"username\": \"fajnyuser\",\n"
                            + "\t\"password\": \"spoko12\"\n"
                            + "}")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    JSONObject jsonObject = new JSONObject(body);
    return jsonObject.getString("accessToken");
  }
}
