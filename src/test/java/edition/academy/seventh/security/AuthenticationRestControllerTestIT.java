package edition.academy.seventh.security;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class AuthenticationRestControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Test
  public void should_registerNewUser_when_sendRequestToProperPath() throws Exception {
    // Given

    // When

    // Then
    this.mockMvc
        .perform(
            post("/auth/sign_up")
                .content(
                    "{\n"
                        + "\t\"email\": \"k@o2.pl\",\n"
                        + "\t\"username\": \"ksundaysky\",\n"
                        + "\t\"role\": [\"admin\"],\n"
                        + "\t\"password\": \"piesek12\"\n"
                        + "}")
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("User registered successfully!")));
  }

  @Test
  public void should_loginUser_when_userAlreadyInDatabase() throws Exception {

    // Given
    this.mockMvc
        .perform(
            post("/auth/sign_up")
                .content(
                    "{\n"
                        + "\t\"email\": \"kfajjasdbjksh2@o2.pl\",\n"
                        + "\t\"username\": \"fajnyuserfajny\",\n"
                        + "\t\"role\": [\"admin\"],\n"
                        + "\t\"password\": \"piesek12\"\n"
                        + "}")
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("User registered successfully!")));

    // When
    String body =
        this.mockMvc
            .perform(
                post("/auth/sign_in")
                    .content(
                        "{\n"
                            + "\t\"username\": \"fajnyuserfajny\",\n"
                            + "\t\"password\": \"piesek12\"\n"
                            + "}")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    JSONObject jsonObject = new JSONObject(body);
    String token = jsonObject.getString("accessToken");

    // Then
    assertFalse(token.isEmpty());
  }
}
