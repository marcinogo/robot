package edition.academy.seventh.scrapping;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class RobotScrappingStarterTestIT {

  @Autowired private MockMvc mockMvc;

  @MockBean private PromotionProviderManager promotionProviderManager;

  @Test
  public void should_returnForbiddenStatus_when_userWithRoleUserTryToStartScrapping()
      throws Exception {

    // Given

    when(promotionProviderManager.getScrappedBooks()).thenReturn(Collections.emptyList());

    this.mockMvc
        .perform(
            post("/auth/sign_up")
                .content(
                    "{\n"
                        + "\t\"email\": \"k2@o2.pl\",\n"
                        + "\t\"username\": \"ksundaysky2\",\n"
                        + "\t\"role\": [\"user\"],\n"
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
    String token = jsonObject.getString("accessToken");

    // Then
    this.mockMvc
        .perform(get("/start").header("Authorization", "Bearer " + token))
        .andExpect(status().isForbidden());
  }

  @Test
  public void should_returnOkStatus_when_userWithRoleAdminTryToStartScrapping() throws Exception {

    // Given

    when(promotionProviderManager.getScrappedBooks()).thenReturn(Collections.emptyList());

    // When
    String body =
        this.mockMvc
            .perform(
                post("/auth/sign_in")
                    .content(
                        "{\n"
                            + "\t\"username\": \"admin\",\n"
                            + "\t\"password\": \"admin12\"\n"
                            + "}")
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    JSONObject jsonObject = new JSONObject(body);
    String token = jsonObject.getString("accessToken");

    // Then
    this.mockMvc
        .perform(get("/start").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }
}
