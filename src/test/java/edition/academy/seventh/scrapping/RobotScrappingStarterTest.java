package edition.academy.seventh.scrapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edition.academy.seventh.Main;
import edition.academy.seventh.security.request.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@EnableWebMvc
@WebAppConfiguration
@ContextConfiguration(classes = {Main.class})
public class RobotScrappingStarterTest extends AbstractTestNGSpringContextTests {

  @Autowired private WebApplicationContext wac;

  private MockMvc mvc;

  @BeforeMethod
  public void prepareMockMvc() {
    this.mvc = webAppContextSetup(wac).build();
  }

  @Test
  public void testController() throws Exception {

//    LoginForm loginForm = new LoginForm("pan_pawel", "tajnehaslo");
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//    String requestJson=ow.writeValueAsString(loginForm);
//
//    this.mvc
//            .perform(post("/auth/sign_in").content(requestJson));

    this.mvc
        .perform(get("/start"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }
}
