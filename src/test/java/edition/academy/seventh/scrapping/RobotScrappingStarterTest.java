package edition.academy.seventh.scrapping;

import edition.academy.seventh.security.model.RoleName;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class RobotScrappingStarterTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void should_do_when_sth() throws Exception {
    // Given
    this.mockMvc
        .perform(
            post("/auth/sign_up")
                .content(
                    "{\n"
                        + "\t\"email\": \"k2@o2.pl\",\n"
                        + "\t\"username\": \"ksundaysky2\",\n"
                        + "\t\"role\": [\"ADMIN\"],\n"
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
                            + "\t\"username\": \"ksundaysky2\",\n"
                            + "\t\"password\": \"piesek12\"\n"
                            + "}")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    JSONObject jsonObject = new JSONObject(body);
    String token = jsonObject.getString("token");

    // Then
//    this.mockMvc
//        .perform(get("/start").param("role", "ADMIN").header("authorization", "Bearer " + token))
//        .andExpect(status().isOk());
    this.mockMvc
            .perform(get("/start").header("Authorization","Bearer "+token).header("authorities", "authority: " + "ADMIN"))
            .andExpect(status().isOk());
  }
}
